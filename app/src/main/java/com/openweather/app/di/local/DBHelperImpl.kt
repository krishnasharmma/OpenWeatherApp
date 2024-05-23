package com.openweather.app.di.local

import com.openweather.app.data.db.dao.HistoryDao
import com.openweather.app.data.db.dao.UsersDao
import com.openweather.app.data.db.entity.Users
import com.openweather.app.data.db.entity.WeatherHistory
import javax.inject.Inject

class DBHelperImpl @Inject constructor(private val usersDao: UsersDao, private val historyDao: HistoryDao):
    DBHelper {

    override suspend fun insertUserData(users: Users) {
        usersDao.insertUser(users)
    }

    override suspend fun getUserList(email: String)= usersDao.getUserList(email)
    override suspend fun insertHistory(weatherHistory: WeatherHistory) {
        historyDao.insertHistory(weatherHistory)
    }

    override suspend fun getHistoryList() = historyDao.getHistoryList()

    override suspend fun deleteAllData() = historyDao.deleteData()
}