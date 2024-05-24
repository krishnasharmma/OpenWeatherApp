package com.openweather.app.repository

import com.openweather.app.data.request.CurrentWeatherRequest
import com.openweather.app.data.remote.ApiInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NetworkRepository @Inject constructor(val apiInteractor: ApiInteractor) {
    fun getCurrentWeather(currentWeatherRequest: CurrentWeatherRequest) = apiInteractor.getCurrentWeather(currentWeatherRequest).flowOn(Dispatchers.IO)
}