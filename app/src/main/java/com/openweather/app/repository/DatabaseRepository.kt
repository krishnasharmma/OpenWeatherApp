package com.openweather.app.repository

import com.openweather.app.data.db.entity.Users
import com.openweather.app.data.db.entity.WeatherHistory
import com.openweather.app.data.db.interactor.DatabaseInteractor
import javax.inject.Inject


open class DatabaseRepository @Inject constructor(private val databaseInteractor: DatabaseInteractor) {
    open suspend fun insertUserData(users: Users) {
        databaseInteractor.insertUserData(users)
    }

    open suspend fun getUserList(email: String) = databaseInteractor.getUserList(email)

    open suspend fun insertHistoryData(weatherHistory: WeatherHistory) {
        databaseInteractor.insertHistory(weatherHistory)
    }

    open suspend fun getHistoryList() = databaseInteractor.getHistoryList()

    open suspend fun deleteAllData() = databaseInteractor.deleteAllData()
}