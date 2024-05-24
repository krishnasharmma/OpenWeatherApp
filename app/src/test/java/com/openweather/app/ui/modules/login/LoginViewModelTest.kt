package com.openweather.app.ui.modules.login

import com.openweather.app.data.db.entity.Users
import com.openweather.app.repository.DatabaseRepository
import com.openweather.app.utils.StringConstants
import com.openweather.app.CoroutineRule
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var databaseRepository: DatabaseRepository

    // Use @Rule to apply the CoroutineRule
    @get:Rule
    val coroutineRule: CoroutineRule = CoroutineRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineRule.testDispatcher) // Set the main dispatcher
        databaseRepository = mockk()
        loginViewModel = LoginViewModel(databaseRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after testing
        clearAllMocks()
    }

    @Test
    fun `checkIfUserExist should invoke callback with success`() = runBlockingTest {
        // Arrange
        val existingUser = Users(
            userName = "testUser",
            userPass = "password123"
        )
        coEvery { databaseRepository.getUserList(existingUser.userName) } returns flowOf(listOf(existingUser))

        // Act
        loginViewModel.checkIfUserExist(existingUser) { result ->
            // Assert
            assert(result == StringConstants.LOGIN_SUCCESS)
        }
    }

    @Test
    fun `test determineLoginResult`() = runBlockingTest {
        // Arrange
        val existingUser = Users(
            userName = "testUser",
            userPass = "password123"
        )
        coEvery { databaseRepository.getUserList(existingUser.userName) } returns flowOf(listOf(existingUser))

        // Act
        val result = LoginViewModel(databaseRepository).determineLoginResult(existingUser)

        // Assert
        assertEquals(StringConstants.LOGIN_SUCCESS, result)
    }

    @Test
    fun `test Incorrect Password`() = runBlockingTest {
        // Arrange
        val existingUser = Users(
            userName = "testUser",
            userPass = "password123"
        )
        coEvery { databaseRepository.getUserList(existingUser.userName) } returns flowOf(listOf(existingUser))

        // Act
        val result = loginViewModel.determineLoginResult(
            Users(
                userName = "testUser",
                userPass = "wrongPassword"
            )
        )

        assertEquals(StringConstants.PASS_MISMATCH, result)
    }

    @Test
    fun `test User Not Found`() = runBlockingTest {
        // Arrange
        coEvery { databaseRepository.getUserList("nonExistingUser") } returns flowOf(emptyList())

        // Act
        val result = loginViewModel.determineLoginResult(
            Users(
                userName = "nonExistingUser",
                userPass = "password"
            )
        )

        assertEquals(StringConstants.EMAIL_NOT_FOUND, result)
    }

    @Test
    fun `test Delete Data`() = runBlockingTest {
        // Arrange
        coEvery { databaseRepository.deleteAllData() } returns Unit

        // Act
        loginViewModel.deleteData()

    }

}
