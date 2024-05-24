package com.openweather.app.data.dao

import com.openweather.app.data.db.dao.UsersDao
import com.openweather.app.data.db.entity.Users
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeUsersDao: UsersDao {

    val usersList = ArrayList<Users>()

    override fun insertUser(users: Users) {
        usersList.add(users)
    }

    override fun getUserList(email: String): Flow<List<Users>> {
        return flowOf(usersList)
    }
}