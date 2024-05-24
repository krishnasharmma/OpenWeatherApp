package com.openweather.app.di.remote

import com.openweather.app.data.remote.ApiInteractorImpl
import com.openweather.app.data.remote.ApiService
import com.openweather.app.data.request.CurrentWeatherRequest
import com.openweather.app.utils.Resource
import com.openweather.app.utils.Status
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

import com.openweather.app.generateWeatherDto
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList

@OptIn(ExperimentalCoroutinesApi::class)
class ApiInteractorImplTest {

    private lateinit var apiInteractorImpl: ApiInteractorImpl
    private lateinit var mockApiService: ApiService

    @Before
    fun setUp() {
        mockApiService = mockk()
        apiInteractorImpl = ApiInteractorImpl(mockApiService)
    }

    @Test
    fun `getCurrentWeather success should emit Loading and Success`() = runBlockingTest {
        // Arrange
        val currentWeatherRequest = CurrentWeatherRequest(apiKey = "fakeKey", lat = 0.0, lon = 0.0)
        val mockApiResponse = generateWeatherDto()

        coEvery {
            mockApiService.getCurrentWeatherData(currentWeatherRequest.apiKey, currentWeatherRequest.lat, currentWeatherRequest.lon)
        } returns mockApiResponse

        // Act
        val result = apiInteractorImpl.getCurrentWeather(currentWeatherRequest).toList()

        // Assert
        assertEquals(2, result.size) // Loading and Success emitted
        assertTrue(result[0].status == Status.LOADING)
        assertTrue(result[1].status == Status.SUCCESS)
        assertEquals(mockApiResponse, (result[1] as Resource<*>).data)
    }

    @Test
    fun `getCurrentWeather error should emit Loading and Error`() = runBlockingTest {
        // Arrange
        val currentWeatherRequest = CurrentWeatherRequest(apiKey = "fakeKey", lat = 0.0, lon = 0.0)
        val mockException = RuntimeException("Fake error message")

        coEvery {
            mockApiService.getCurrentWeatherData(currentWeatherRequest.apiKey, currentWeatherRequest.lat, currentWeatherRequest.lon)
        } throws mockException

        // Act
        val result = apiInteractorImpl.getCurrentWeather(currentWeatherRequest).toList()

        // Assert
        assertEquals(2, result.size) // Loading and Error emitted
        assertTrue(result[0].status == Status.LOADING)
        assertTrue(result[1].status == Status.ERROR)
        assertEquals(mockException.localizedMessage, (result[1] as Resource<*>).message)
    }
}
