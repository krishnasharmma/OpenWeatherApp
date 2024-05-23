package com.openweather.app.di.remote

import com.openweather.app.data.request.CurrentWeatherRequest
import com.openweather.app.data.response.CurrentLocationResponse
import com.openweather.app.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ApiHelper {

    fun getCurrentWeather(currentWeatherRequest: CurrentWeatherRequest): Flow<Resource<CurrentLocationResponse>>
}