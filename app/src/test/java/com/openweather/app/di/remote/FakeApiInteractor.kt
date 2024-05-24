package com.openweather.app.di.remote

import com.openweather.app.data.request.CurrentWeatherRequest
import com.openweather.app.data.response.CurrentLocationResponse
import com.openweather.app.utils.Resource
import com.openweather.app.generateWeatherDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeApiInteractor: ApiInteractor {
    var weatherdto = generateWeatherDto()
    override fun getCurrentWeather(currentWeatherRequest: CurrentWeatherRequest): Flow<Resource<CurrentLocationResponse>> {
        return flowOf(Resource.Success(weatherdto))
    }
}