package com.openweather.app.ui.modules.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.openweather.app.R
import com.openweather.app.data.db.entity.Users
import com.openweather.app.ui.transformation.PasswordVisualTransformation
import com.openweather.app.utils.StringConstants
import com.openweather.app.utils.extension.get
import com.openweather.app.utils.extension.isEmailValid
import com.openweather.app.utils.extension.myAppPreferences
import com.openweather.app.utils.extension.set
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel) {
    Column (modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){

        val context = LocalContext.current

        var nameFieldValue by remember {
            mutableStateOf("")
        }

        var passWordFieldValue by remember {
            mutableStateOf("")
        }

        val focusManager = LocalFocusManager.current

        val scope = rememberCoroutineScope()

        Image(painter = painterResource(id = R.drawable.login_img), contentDescription = "")
        Spacer(modifier = Modifier.size(20.dp))

        Text(text = StringConstants.helloTxt, style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold), modifier = Modifier.fillMaxWidth())


        Text(text = StringConstants.welcomeTxt, style = TextStyle(fontSize = 14.sp), modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = nameFieldValue, onValueChange = {
            nameFieldValue = it
        }, placeholder = {
            Text(StringConstants.enterEmail)
        },keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
        ), keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )
        )

        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = passWordFieldValue, onValueChange = {
            passWordFieldValue = it
        },keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
        ),
            placeholder = {
                Text(StringConstants.enterPassword)
            }, visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.size(80.dp))

        Button(onClick = {
            if (nameFieldValue.isNotEmpty() && passWordFieldValue.isNotEmpty() && nameFieldValue.isEmailValid()) {
                val users = Users(
                    userName = nameFieldValue,
                    userPass = passWordFieldValue
                )
                loginViewModel.checkIfUserExist(users){
                    scope.launch(Dispatchers.Main) {
                        if (it == StringConstants.LOGIN_SUCCESS){
                            if(context.myAppPreferences.get(StringConstants.savedUserConst,"") != users.userName) {
                                loginViewModel.deleteData()
                            }
                            context.myAppPreferences[StringConstants.sessionConst] = true
                            context.myAppPreferences[StringConstants.savedUserConst] = users.userName
                            navController.navigate(StringConstants.homeRoute) {
                                popUpTo(StringConstants.loginRoute) {
                                    inclusive = true
                                }
                            }
                        } else {
                            Toast.makeText(context, if (it == StringConstants.PASS_MISMATCH) StringConstants.passwordError else StringConstants.emailNotFoundError, Toast.LENGTH_SHORT).show()
                            if (it != StringConstants.PASS_MISMATCH) {
                                navController.navigate(StringConstants.registrationRoute)
                            }
                        }
                    }

                }
            } else {
                Toast.makeText(context, StringConstants.invalidEmailError,Toast.LENGTH_SHORT).show()
            }
        }, modifier = Modifier.width(220.dp)) {
            Text(text = StringConstants.loginScreen, style = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp)),color = Color.White)
        }

        Spacer(modifier = Modifier.size(20.dp))

        Text(text = StringConstants.createAccountTxt, textDecoration = TextDecoration.Underline, color = Color.Gray, modifier = Modifier.clickable {
            navController.navigate(StringConstants.registrationRoute)
        })
    }
}