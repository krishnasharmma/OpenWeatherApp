package com.openweather.app.data.db

import androidx.annotation.Keep
import androidx.room.Database
import androidx.room.RoomDatabase
import com.openweather.app.data.db.dao.UsersDao
import com.openweather.app.data.db.entity.Users

@Keep
@Database(entities = [Users::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}