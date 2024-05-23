package com.openweather.app.data.db

import androidx.annotation.Keep
import androidx.room.Database
import androidx.room.RoomDatabase
import com.openweather.app.data.db.dao.HistoryDao
import com.openweather.app.data.db.dao.UsersDao
import com.openweather.app.data.db.entity.Users
import com.openweather.app.data.db.entity.WeatherHistory

@Keep
@Database(entities = [Users::class, WeatherHistory::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun usersDao(): UsersDao
    abstract fun historyDao(): HistoryDao
}