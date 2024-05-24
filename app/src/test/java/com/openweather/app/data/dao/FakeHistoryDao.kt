package com.openweather.app.data.dao

import com.openweather.app.data.db.dao.HistoryDao
import com.openweather.app.data.db.entity.WeatherHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeHistoryDao: HistoryDao {

    val historyList = ArrayList<WeatherHistory>()

    override fun insertHistory(users: WeatherHistory) {
        historyList.add(users)
    }

    override fun getHistoryList(): Flow<List<WeatherHistory>> {
        return flowOf(historyList)
    }

    override fun deleteData() {
        historyList.clear()
    }
}