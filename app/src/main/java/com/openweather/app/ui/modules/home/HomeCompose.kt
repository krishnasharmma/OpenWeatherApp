package com.openweather.app.ui.modules.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.openweather.app.R
import com.openweather.app.data.request.CurrentWeatherRequest
import com.openweather.app.ui.common.CommonTextWithSpan
import com.openweather.app.utils.Constants
import com.openweather.app.utils.StringConstants
import com.openweather.app.utils.extension.convertTemperatureToCelsius
import com.openweather.app.utils.extension.convertTimeMillisToFormattedString
import com.openweather.app.utils.extension.getCountryNameFromCode
import com.openweather.app.utils.extension.myAppPreferences
import com.openweather.app.utils.extension.set

@Composable
fun HomeScreen(homeViewModel: HomeViewModel, navController: NavHostController){
    Column (Modifier.fillMaxSize()){
        TabScreen(homeViewModel, navController)
    }
}

@Composable
fun TabScreen(homeViewModel: HomeViewModel, navController: NavHostController) {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Current", "History")
    val shape = RoundedCornerShape(30.dp) //define border radius here
    Column {
        Row (modifier = Modifier.padding(30.dp)){
            TabRow(selectedTabIndex = tabIndex,
                modifier = Modifier
                    .clip(
                        shape = shape
                    )
                    .border(1.dp, Color(0xFF016158), shape = shape),
                indicator = {
                    TabRowDefaults.Indicator(
                        color = Color.Transparent
                    )
                },
                backgroundColor = Color.White) {
                tabs.forEachIndexed { index, title ->
                    Tab(selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        text = { Text(text = title, maxLines = 1, softWrap = false)},
                        modifier = Modifier
                            .background(if (tabIndex == index) Color(0xFF016158) else Color.White)
                            .drawBehind {
                                drawLine(
                                    Color(0xFF016158),
                                    Offset(size.width, 0f),
                                    Offset(size.width, size.height),
                                    4f
                                )
                            },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color(0xFF016158)
                    )
                }
            }

        }
        when (tabIndex) {
            0 -> CurrentWeather(homeViewModel, navController)
            1 -> HistoryWeather(homeViewModel)
        }

    }
}

@Composable
fun CurrentWeather(homeViewModel: HomeViewModel, navController: NavHostController) {
    val locationData = homeViewModel.getLocationData().observeAsState()

    val context = LocalContext.current

    if (locationData.value != null){
        SideEffect {
            if (locationData.value != null) {
                val currentWeatherRequest = CurrentWeatherRequest(
                    locationData.value?.latitude!!,
                    locationData.value?.longitude!!
                )
                homeViewModel.getCurrentWeatherData(currentWeatherRequest)
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = homeViewModel.weatherResponseState.value.name?:"",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = homeViewModel.weatherResponseState.value.sys?.country?.getCountryNameFromCode()?:"",
            style = TextStyle(fontSize = 14.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.size(30.dp))

        Text(
            text = homeViewModel.weatherResponseState.value.main?.temp?.convertTemperatureToCelsius()?:"",
            style = TextStyle(fontSize = 44.sp, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )


        AsyncImage(
            model = Constants.getImageUrl() + "${homeViewModel.weatherResponseState.value.weather?.get(0)?.icon}@2x.png",
            contentDescription = "", modifier = Modifier.size(190.dp)
        )

        Text(
            text = homeViewModel.weatherResponseState.value.weather?.get(0)?.description ?: "",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = System.currentTimeMillis().convertTimeMillisToFormattedString(
                StringConstants.savedTimePattern, false) ?: "",
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(70.dp))



        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Row (verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(id = R.drawable.sunny), contentDescription = "", modifier = Modifier.size(60.dp))
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = homeViewModel.weatherResponseState.value.sys?.sunrise?.convertTimeMillisToFormattedString()?:"",
                    color= Color.Black, style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold))
            }

            Row (verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(id = R.drawable.sun), contentDescription = "", modifier = Modifier.size(60.dp))
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = homeViewModel.weatherResponseState.value.sys?.sunset?.convertTimeMillisToFormattedString()?:"",
                    color = Color.Black, style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold))
            }
        }

        Spacer(modifier = Modifier.size(20.dp))


        Spacer(modifier = Modifier.weight(1f))

        Text(
            "Logout",
            modifier = Modifier.clickable {
                                      context.myAppPreferences[StringConstants.sessionConst] = false
                navController.navigate(StringConstants.loginRoute){
                    popUpTo(StringConstants.homeRoute) {
                        inclusive = true
                    }
                }
            },
            textDecoration = TextDecoration.Underline,
            color = Color.Gray
        )
    }

}