package com.openweather.app.di.local

import com.openweather.app.data.db.entity.Users
import com.openweather.app.data.db.entity.WeatherHistory
import kotlinx.coroutines.flow.Flow

interface DatabaseInteractor {

    suspend fun insertUserData(users: Users)

    suspend fun getUserList(email: String): Flow<List<Users>>

    suspend fun insertHistory(weatherHistory: WeatherHistory)

    suspend fun getHistoryList(): Flow<List<WeatherHistory>>

    suspend fun deleteAllData()
}