package com.openweather.app.repository

import com.openweather.app.di.remote.FakeApiInteractor
import com.openweather.app.generateCurrentWeatherRequest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class NetworkRepositoryTest {

    lateinit var fakeApiHelper: FakeApiInteractor
    lateinit var networkRepository: NetworkRepository

    @Before
    fun setUp() {
        fakeApiHelper = FakeApiInteractor()
        networkRepository = NetworkRepository(fakeApiHelper)
    }

    @Test
    fun `Should get CurrentWeather successfully`() = runBlockingTest {
        var result = networkRepository.getCurrentWeather(generateCurrentWeatherRequest()).last().data

        result.shouldEqual(fakeApiHelper.weatherdto)
    }
}