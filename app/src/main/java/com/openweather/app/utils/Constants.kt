package com.openweather.app.utils

import androidx.annotation.Keep

@Keep
object Constants {

    init {
        System.loadLibrary("native-lib")
    }

    external fun getBaseUrl(): String

    external fun getImageUrl(): String

}