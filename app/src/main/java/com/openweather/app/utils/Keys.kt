package com.openweather.app.utils

import androidx.annotation.Keep

@Keep
object Keys {
    init {
        System.loadLibrary("app")
        System.loadLibrary("sqlcipher")
    }

    external fun apiKey(): String

    external fun sqlKey(): String
}