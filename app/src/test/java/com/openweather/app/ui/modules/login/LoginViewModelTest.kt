package com.openweather.app.ui.modules.login

import com.openweather.app.data.db.entity.Users
import com.openweather.app.repository.DatabaseRepository
import com.openweather.app.utils.StringConstants
import com.openweather.app.CoroutineRule
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
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

    @get:Rule
    val coroutineRule: CoroutineRule = CoroutineRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineRule.testDispatcher)
        databaseRepository = mockk()
        loginViewModel = LoginViewModel(databaseRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `checkIfUserExist should invoke callback with success`() = runBlockingTest {
        // Arrange
        val existingUser = Users(userName = "testUser", userPass = "password123")
        coEvery { databaseRepository.getUserList(existingUser.userName) } returns flowOf(listOf(existingUser))

        // Act & Assert
        loginViewModel.checkIfUserExist(existingUser) { result ->
            assertEquals(StringConstants.LOGIN_SUCCESS, result)
        }
    }

    @Test
    fun `checkIfUserExist should invoke callback with password mismatch`() = runBlockingTest {
        // Arrange
        val existingUser = Users(userName = "testUser", userPass = "password123")
        coEvery { databaseRepository.getUserList(existingUser.userName) } returns flowOf(listOf(existingUser))

        // Act & Assert
        loginViewModel.checkIfUserExist(existingUser.copy(userPass = "wrongPassword")) { result ->
            assertEquals(StringConstants.PASS_MISMATCH, result)
        }
    }

    @Test
    fun `checkIfUserExist should invoke callback with user not found`() = runBlockingTest {
        // Arrange
        val nonExistingUser = Users(userName = "nonExistingUser", userPass = "password")
        coEvery { databaseRepository.getUserList(nonExistingUser.userName) } returns flowOf(emptyList())

        // Act & Assert
        loginViewModel.checkIfUserExist(nonExistingUser) { result ->
            assertEquals(StringConstants.EMAIL_NOT_FOUND, result)
        }
    }

    @Test
    fun `test determineLoginResult with valid user`() = runBlockingTest {
        // Arrange
        val existingUser = Users(userName = "testUser", userPass = "password123")
        coEvery { databaseRepository.getUserList(existingUser.userName) } returns flowOf(listOf(existingUser))

        // Act
        val result = loginViewModel.determineLoginResult(existingUser)

        // Assert
        assertEquals(StringConstants.LOGIN_SUCCESS, result)
    }

    @Test
    fun `test determineLoginResult with incorrect password`() = runBlockingTest {
        // Arrange
        val existingUser = Users(userName = "testUser", userPass = "password123")
        coEvery { databaseRepository.getUserList(existingUser.userName) } returns flowOf(listOf(existingUser))

        // Act
        val result = loginViewModel.determineLoginResult(existingUser.copy(userPass = "wrongPassword"))

        // Assert
        assertEquals(StringConstants.PASS_MISMATCH, result)
    }

    @Test
    fun `test determineLoginResult with user not found`() = runBlockingTest {
        // Arrange
        coEvery { databaseRepository.getUserList("nonExistingUser") } returns flowOf(emptyList())

        // Act
        val result = loginViewModel.determineLoginResult(Users(userName = "nonExistingUser", userPass = "password"))

        // Assert
        assertEquals(StringConstants.EMAIL_NOT_FOUND, result)
    }

    @Test
    fun `test deleteData should complete successfully`() = runBlockingTest {
        // Arrange
        coEvery { databaseRepository.deleteAllData() } returns Unit

        // Act
        loginViewModel.deleteData()

        // Assert
        coVerify { databaseRepository.deleteAllData() }
    }
}

