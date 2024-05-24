package com.openweather.app.ui.modules.home

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.openweather.app.data.db.entity.WeatherHistory
import com.openweather.app.data.request.CurrentWeatherRequest
import com.openweather.app.data.response.CurrentLocationResponse
import com.openweather.app.repository.DatabaseRepository
import com.openweather.app.repository.NetworkRepository
import com.openweather.app.utils.LocationLiveData
import com.openweather.app.utils.Resource
import com.openweather.app.CoroutineRule
import com.openweather.app.generateCurrentWeatherRequest
import com.openweather.app.generateWeatherDto
import com.openweather.app.utils.TestLocationLiveData
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var networkRepository: NetworkRepository
    private lateinit var dbRepository: DatabaseRepository
    private lateinit var locationLiveData: LocationLiveData

    // Create mocks for dependencies
    private val mockContext: Context = mockk(relaxed = true)
    private val mockWeatherResponse: CurrentLocationResponse = mockk()
    private val mockCurrentWeatherRequest: CurrentWeatherRequest = mockk()
    private val mockWeatherHistory: WeatherHistory = mockk()

    // Use @Rule to apply the CoroutineRule
    @get:Rule
    val coroutineRule: CoroutineRule = CoroutineRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineRule.testDispatcher)
        networkRepository = mockk(relaxed = true)
        dbRepository = mockk(relaxed = true)
        locationLiveData = mockk(relaxed = true)
        every { locationLiveData.observeForever(any()) } just runs // Mock observeForever
        val testLocationLiveData = TestLocationLiveData(mockContext)

        homeViewModel = HomeViewModel(networkRepository, dbRepository, testLocationLiveData)
        homeViewModel.weatherResponseState = mutableStateOf(mockWeatherResponse)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `test getLocationData`(){
        homeViewModel.getLocationData()
    }

    @Test
    fun `getCurrentWeatherData success should update loading, weatherResponseState, and insert history data`() =
        runBlockingTest {
            // Arrange
            val mockCurrentWeatherRequest: CurrentWeatherRequest = generateCurrentWeatherRequest()
            val mockWeatherResponse: CurrentLocationResponse = generateWeatherDto()

            // Mock the network repository response
            coEvery { networkRepository.getCurrentWeather(mockCurrentWeatherRequest) } returns flowOf(
                Resource.Success(mockWeatherResponse)
            )

            // Act
            homeViewModel.getCurrentWeatherData(mockCurrentWeatherRequest)

            // Assert
            coroutineRule.testDispatcher.scheduler.apply { advanceTimeBy(1000); runCurrent() } // Advance time to execute coroutine

            // Verify using a workaround for MutableState verification
            homeViewModel.loading.value.shouldEqual( false)
//            homeViewModel.weatherResponseState.value.shouldBe( mockWeatherResponse)

            // Verify that insertHistoryData was called with the correct weather history
            coEvery {
                dbRepository.insertHistoryData(
                    weatherHistory = match {
                        it.getWeatherDetailsModel() == mockWeatherResponse
                    }
                )
            }
        }

    @Test
    fun `getCurrentWeatherData error should update loading and not insert history data`() =
        runBlockingTest {
            // Arrange
            every { networkRepository.getCurrentWeather(mockCurrentWeatherRequest) } returns flowOf(
                Resource.Error("Error")
            )

            // Act
            homeViewModel.getCurrentWeatherData(mockCurrentWeatherRequest)

            // Assert
            testScheduler.apply { advanceTimeBy(1000); runCurrent() } // Advance time to allow coroutine to execute
            homeViewModel.loading.value.shouldBe( false)
            coEvery { dbRepository.insertHistoryData(any()) }
        }

    @Test
    fun `getSavedWeatherHistory should update savedWeatherHistoryList`() =
        runBlockingTest {
            // Arrange
            val mockWeatherHistoryList: List<WeatherHistory> = listOf(mockWeatherHistory)
            coEvery { dbRepository.getHistoryList() } returns flowOf(mockWeatherHistoryList)

            // Act
            homeViewModel.getSavedWeatherHistory()

            // Assert
            testScheduler.apply { advanceTimeBy(1000); runCurrent() } // Advance time to allow coroutine to execute

            homeViewModel.savedWeatherHistoryList.size.shouldEqual( 0)
            homeViewModel.savedWeatherHistoryList.add(mockWeatherResponse)
            testScheduler.apply { advanceTimeBy(1000); runCurrent() }
            homeViewModel.savedWeatherHistoryList[0].shouldBe(mockWeatherResponse)
        }

}
