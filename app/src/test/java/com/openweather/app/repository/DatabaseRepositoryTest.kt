package com.openweather.app.repository

import com.google.common.truth.Truth
import com.openweather.app.data.db.interactor.DatabaseInteractor
import com.openweather.app.di.local.FakeDatabaseInteractor
import com.openweather.app.generateUsers
import com.openweather.app.generateWeatherHistory
import com.openweather.app.randomString
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class DatabaseRepositoryTest {

    lateinit var databaseInteractor: DatabaseInteractor
    lateinit var databaseRepository: DatabaseRepository

    @Before
    fun setUp() {
        databaseInteractor = FakeDatabaseInteractor()
        databaseRepository = DatabaseRepository(databaseInteractor)
    }

    @Test
    fun `Should insert Users in db successfully`() = runBlockingTest {
        val cityInfoDto = generateUsers()

        databaseInteractor.insertUserData(cityInfoDto)

        databaseRepository.insertUserData(cityInfoDto)

        Truth.assertThat(true).isTrue()
    }

    @Test
    fun `Should get Users from db successfully`() = runBlockingTest {
        val cityInfoDto = generateUsers()

        databaseInteractor.insertUserData(cityInfoDto)

        var result = databaseRepository.getUserList(randomString()).last().get(0)

        result.shouldEqual(cityInfoDto)
    }

    @Test
    fun `Should insert WeatherHistory in db successfully`() = runBlockingTest {
        val cityInfoDto = generateWeatherHistory()

        databaseInteractor.insertHistory(cityInfoDto)

        databaseRepository.insertHistoryData(cityInfoDto)

        Truth.assertThat(true).isTrue()
    }

    @Test
    fun `Should get WeatherHistoryList from db successfully`() = runBlockingTest {
        val cityInfoDto = generateWeatherHistory()

        databaseInteractor.insertHistory(cityInfoDto)

        var result = databaseRepository.getHistoryList().last().get(0)

        result.shouldEqual(cityInfoDto)
    }

    @Test
    fun `deleteAllData Should delete WeatherHistoryList data from db successfully`() = runBlockingTest {

        databaseInteractor.deleteAllData()

        var result = databaseRepository.getHistoryList().last()

        result.size.shouldEqual(0)
    }

    @Test
    fun `deleteAllData should delete WeatherHistoryList db successfully`() = runBlockingTest {
        // Perform some insertion to ensure there is data
        val cityInfoDto = generateWeatherHistory()
        databaseRepository.insertHistoryData(cityInfoDto)

        // Delete data
        databaseRepository.deleteAllData()

        // Introduce a short delay to allow the asynchronous deletion to complete
        delay(100)

        // Check the result
        val result = databaseRepository.getHistoryList().last()

        result.size.shouldEqual(0)
    }
}