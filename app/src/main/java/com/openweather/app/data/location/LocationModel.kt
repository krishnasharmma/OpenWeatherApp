package com.openweather.app.data.location

import androidx.annotation.Keep

@Keep
data class LocationModel(
    val longitude: Double,
    val latitude: Double
)