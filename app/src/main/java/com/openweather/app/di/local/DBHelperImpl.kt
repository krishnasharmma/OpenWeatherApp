package com.openweather.app.di.local

import com.openweather.app.data.db.dao.UsersDao
import com.openweather.app.data.db.entity.Users
import javax.inject.Inject

class DBHelperImpl @Inject constructor(private val usersDao: UsersDao):
    DBHelper {

    override suspend fun insertUserData(users: Users) {
        usersDao.insertUser(users)
    }

    override suspend fun getUserList(email: String)= usersDao.getUserList(email)

    override suspend fun deleteAllData()  {
        //TODO - remove history data
    }
}