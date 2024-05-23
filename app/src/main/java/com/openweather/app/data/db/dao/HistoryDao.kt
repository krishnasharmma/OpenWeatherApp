package com.openweather.app.data.db.dao

import androidx.annotation.Keep
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openweather.app.data.db.entity.WeatherHistory
import kotlinx.coroutines.flow.Flow

@Keep
@Dao
interface HistoryDao {
    @Insert
    fun insertHistory(users: WeatherHistory)

    @Query("select * from weatherhistory")
    fun getHistoryList(): Flow<List<WeatherHistory>>

    @Query("DELETE FROM weatherhistory")
    fun deleteData()
}