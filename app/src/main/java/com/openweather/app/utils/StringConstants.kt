package com.openweather.app.utils

import androidx.annotation.Keep

@Keep
object StringConstants {
    val cityConst = "City:"
    val countryConst = "Country:"
    val sunriseConst = "Sunrise:"
    val sunsetConst = "Sunset:"
    val tempConst = "Temp:"

    val timePattern = "HH:mm:ss"
    val savedTimePattern = "dd MMM, HH:mm"
    val sessionConst = "loggedin"
    val savedUserConst = "userEmail"

    //routes
    val homeRoute = "home"
    val loginRoute = "login"
    val registrationRoute = "registration"

    //error codes
    val LOGIN_SUCCESS = 200
    val EMAIL_NOT_FOUND = 404
    val PASS_MISMATCH = 402

    //msgs
    val passwordError = "Wrong Password"
    val emailNotFoundError = "Email not found in db"
    val invalidEmailError = "Please enter valid email or enter all fields"
    val passwordConfirmError = "Password does not match"
    val alreadyRegisteredUserMsg = "User is already registered, logging in"

    //Screen Titles
    val loginScreen = "Login"
    val registrationScreen = "Registration"

    //PlaceHolders
    val enterEmail = "Enter Email"
    val enterPassword = "Enter Password"
    val confirmPassword = "Confirm Password"

    //Button text
    val submitText = "Submit"
    val createAccountTxt = "Create Account"

    val helloTxt = "Hello"
    val welcomeTxt = "Welcome to Open Weather App"
    val createAccTxt = "let's create your account"
}