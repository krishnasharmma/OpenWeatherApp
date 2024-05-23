package com.openweather.app.ui.modules.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.openweather.app.data.response.CurrentLocationResponse
import com.openweather.app.ui.common.HistoryItem
import com.openweather.app.utils.Constants
import com.openweather.app.utils.StringConstants
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Composable
fun HistoryWeather(homeViewModel: HomeViewModel) {
    SideEffect {
        homeViewModel.getSavedWeatherHistory()
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn (modifier = Modifier){
            items(homeViewModel.savedWeatherHistoryList.toList().reversed()) { currentLocation ->
                CurrentLocationItem(currentLocation = currentLocation)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrentLocationItem(currentLocation: CurrentLocationResponse) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp), backgroundColor = Color(0xFFFFFFFF), elevation = 8.dp, shape = RoundedCornerShape(8.dp)
    ) {
        Row (
            Modifier
                .padding(16.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
            Column {
                HistoryItem(StringConstants.cityConst, currentLocation.name)

                Spacer(modifier = Modifier.size(5.dp))

                HistoryItem(StringConstants.tempConst, "" + String.format("%.2f", (currentLocation.main?.temp!! - 273.15))
                    .toDouble() + "°C")

                Spacer(modifier = Modifier.size(5.dp))

                val sunriseTime = DateTimeFormatter.ofPattern(StringConstants.timePattern)
                    .withZone(ZoneId.systemDefault())
                    .format(Instant.ofEpochMilli(currentLocation.sys?.sunrise!! * 1000))
                HistoryItem(StringConstants.sunriseConst, sunriseTime)

                Spacer(modifier = Modifier.size(5.dp))

                val sunsetTime = DateTimeFormatter.ofPattern(StringConstants.timePattern)
                    .withZone(ZoneId.systemDefault())
                    .format(Instant.ofEpochMilli(currentLocation.sys.sunset * 1000))
                HistoryItem(StringConstants.sunsetConst, sunsetTime)
            }

            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = currentLocation.timeSaved?: "")
                Spacer(modifier = Modifier.size(5.dp))
                AsyncImage(
                    model = Constants.getImageUrl() + "${currentLocation.weather?.get(0)?.icon!!}@2x.png",
                    contentDescription = "", modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}