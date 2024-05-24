package com.openweather.app.di.remote


import android.os.Build
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import com.openweather.app.data.request.CurrentWeatherRequest
import com.openweather.app.data.response.CurrentLocationResponse
import com.openweather.app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@Keep
class ApiInteractorImpl @Inject constructor(val apiService: ApiService): ApiInteractor {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCurrentWeather(currentWeatherRequest: CurrentWeatherRequest): Flow<Resource<CurrentLocationResponse>> = flow {
        emit(Resource.Loading())
        try {
            val apiResponse = apiService.getCurrentWeatherData(currentWeatherRequest.apiKey,currentWeatherRequest.lat, currentWeatherRequest.lon)
            emit(Resource.Success(apiResponse))
        } catch (ex: Exception){
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage))
        }
    }
}