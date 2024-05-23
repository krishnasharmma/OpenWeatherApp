package com.openweather.app.data.db.entity

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.openweather.app.data.response.CurrentLocationResponse

@Keep
@Entity
data class WeatherHistory(
    @ColumnInfo("weatherDetails")val weatherDetails: String,){
    @PrimaryKey(autoGenerate = true) var id: Int = 0

    fun getWeatherDetailsModel(): CurrentLocationResponse {
        return Gson().fromJson(weatherDetails, CurrentLocationResponse::class.java)
    }
}