package com.openweather.app.repository

import com.openweather.app.data.request.CurrentWeatherRequest
import com.openweather.app.di.remote.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NetworkRepository @Inject constructor(val apiHelper: ApiHelper) {
    fun getCurrentWeather(currentWeatherRequest: CurrentWeatherRequest) = apiHelper.getCurrentWeather(currentWeatherRequest).flowOn(Dispatchers.IO)
}