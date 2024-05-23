package com.openweather.app.repository

import com.openweather.app.data.db.entity.Users
import com.openweather.app.data.db.entity.WeatherHistory
import com.openweather.app.di.local.DBHelper
import javax.inject.Inject


open class DatabaseRepository @Inject constructor(private val dbHelper: DBHelper) {
    open suspend fun insertUserData(users: Users) {
        dbHelper.insertUserData(users)
    }

    open suspend fun getUserList(email: String) = dbHelper.getUserList(email)

    open suspend fun insertHistoryData(weatherHistory: WeatherHistory) {
        dbHelper.insertHistory(weatherHistory)
    }

    open suspend fun getHistoryList() = dbHelper.getHistoryList()

    open suspend fun deleteAllData() = dbHelper.deleteAllData()
}