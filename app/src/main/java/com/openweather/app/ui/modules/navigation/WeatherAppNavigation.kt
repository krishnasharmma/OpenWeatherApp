package com.openweather.app.ui.modules.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.openweather.app.ui.modules.login.LoginScreen
import com.openweather.app.utils.StringConstants
import com.openweather.app.utils.extension.get
import com.openweather.app.utils.extension.myAppPreferences
import com.openweather.app.ui.modules.login.LoginViewModel
import com.openweather.app.ui.modules.registration.RegistrationScreen
import com.openweather.app.ui.modules.registration.RegistrationViewModel


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherAppNavigation(
    navController: NavHostController,
    locationPermissionState: MultiplePermissionsState, ) {
    val context = LocalContext.current
    NavHost(navController, startDestination = if (context.myAppPreferences.get(StringConstants.sessionConst, false)) StringConstants.homeRoute else StringConstants.loginRoute, modifier = Modifier.padding(16.dp),) {
        composable(route = StringConstants.loginRoute) {
            LoginScreen(navController, hiltViewModel<LoginViewModel>())
        }
        composable(route = StringConstants.registrationRoute) {
            RegistrationScreen(navController, hiltViewModel<RegistrationViewModel>())
        }


    }
}