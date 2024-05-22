package com.openweather.app.di.local

import com.openweather.app.data.db.entity.Users
import kotlinx.coroutines.flow.Flow

interface DBHelper {

    suspend fun insertUserData(users: Users)

    suspend fun getUserList(email: String): Flow<List<Users>>

    suspend fun deleteAllData()
}