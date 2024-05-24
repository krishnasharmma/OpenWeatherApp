package com.openweather.app.di.local

import com.openweather.app.data.db.entity.Users
import com.openweather.app.data.db.entity.WeatherHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDatabaseInteractor(): DatabaseInteractor {

    val historyList = ArrayList<WeatherHistory>()

    val usersList = ArrayList<Users>()

    override suspend fun insertUserData(users: Users) {
        usersList.add(users)
    }

    override suspend fun getUserList(email: String): Flow<List<Users>> {
        return flowOf(usersList)
    }

    override suspend fun insertHistory(weatherHistory: WeatherHistory) {
        historyList.add(weatherHistory)
    }

    override suspend fun getHistoryList(): Flow<List<WeatherHistory>> {
        return flowOf(historyList)
    }

    override suspend fun deleteAllData() {
        historyList.clear()
    }
}