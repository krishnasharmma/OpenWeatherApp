package com.openweather.app.data.request

import androidx.annotation.Keep
import com.openweather.app.utils.Keys
import java.nio.charset.Charset
import java.util.Base64

@Keep
data class CurrentWeatherRequest(val lat: Double, val lon: Double,
val apiKey : String= Base64.getDecoder().decode(Keys.apiKey()).toString(
        Charset.defaultCharset())){

}