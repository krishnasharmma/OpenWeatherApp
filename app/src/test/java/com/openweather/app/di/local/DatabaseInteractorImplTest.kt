package com.openweather.app.di.local

import com.openweather.app.data.dao.FakeHistoryDao
import com.openweather.app.data.dao.FakeUsersDao
import com.openweather.app.generateUsers
import com.openweather.app.generateWeatherHistory
import com.openweather.app.randomString
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test


class DatabaseInteractorImplTest {

    private lateinit var historyDao: FakeHistoryDao
    private lateinit var usersDao: FakeUsersDao
    private lateinit var databaseInteractorImpl: DatabaseInteractorImpl

    @Before
    fun setUp() {
        historyDao = FakeHistoryDao()
        usersDao  = FakeUsersDao()
        databaseInteractorImpl = DatabaseInteractorImpl(usersDao, historyDao = historyDao)
    }

    @Test
    fun `Should insert Users in db successfully`() = runBlockingTest {
        val cityInfoDto = generateUsers()

        usersDao.insertUser(cityInfoDto)

        databaseInteractorImpl.insertUserData(cityInfoDto)

        usersDao.getUserList(cityInfoDto.userName).last().get(0).userName.shouldEqual(cityInfoDto.userName)
    }

    @Test
    fun `Should get Users from db successfully`() = runBlockingTest {
        val cityInfoDto = generateUsers()

        usersDao.insertUser(cityInfoDto)

        var result = databaseInteractorImpl.getUserList(randomString()).last().get(0)

        result.shouldEqual(cityInfoDto)
    }

    @Test
    fun `Should insert WeatherHistory in db successfully`() = runBlockingTest {
        val cityInfoDto = generateWeatherHistory()

        historyDao.insertHistory(cityInfoDto)

        databaseInteractorImpl.insertHistory(cityInfoDto)

        historyDao.getHistoryList().last().get(0).shouldEqual(cityInfoDto)
    }

    @Test
    fun `Should get WeatherHistoryList from db successfully`() = runBlockingTest {
        val cityInfoDto = generateWeatherHistory()

        historyDao.insertHistory(cityInfoDto)

        var result = databaseInteractorImpl.getHistoryList().last().get(0)

        result.shouldEqual(cityInfoDto)
    }

    @Test
    fun `deleteAllData should delete WeatherHistoryList db successfully`() = runBlockingTest {
        // Perform some insertion to ensure there is data
        val cityInfoDto = generateWeatherHistory()
        databaseInteractorImpl.insertHistory(cityInfoDto)

        // Delete data
        databaseInteractorImpl.deleteAllData()

        // Introduce a short delay to allow the asynchronous deletion to complete
        delay(100)

        // Check the result
        val result = databaseInteractorImpl.getHistoryList().last()

        result.size.shouldEqual(0)
    }
}