package com.openweather.app.ui.registration

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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

@Composable
fun RegistrationScreen(navController: NavHostController, registrationViewModel: RegistrationViewModel) {
    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        val context = LocalContext.current
        var nameFieldValue by remember {
            mutableStateOf("")
        }

        var passWordFieldValue by remember {
            mutableStateOf("")
        }

        var confirmPassWordFieldValue by remember {
            mutableStateOf("")
        }
        Image(painter = painterResource(id = R.drawable.registration),
            colorFilter = ColorFilter.tint(color = Color.Gray),
            contentDescription = "")

        Spacer(modifier = Modifier.size(20.dp))

        Text(text = "Registration", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))

        Spacer(modifier = Modifier.size(20.dp))

        TextField(modifier = Modifier.fillMaxWidth(),value = nameFieldValue, onValueChange = {
            nameFieldValue = it
        }, placeholder = {
            Text("Enter Email")
        },keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        )
        )

        Spacer(modifier = Modifier.size(20.dp))

        TextField(modifier = Modifier.fillMaxWidth(),value = passWordFieldValue, onValueChange = {
            passWordFieldValue = it
        },keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
            placeholder = {
                Text("Enter Password")
            }, visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.size(20.dp))

        TextField(modifier = Modifier.fillMaxWidth(),value = confirmPassWordFieldValue, onValueChange = {
            confirmPassWordFieldValue = it
        },keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
            placeholder = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.size(40.dp))

        Button(onClick = {
            if (nameFieldValue.isNotEmpty() && passWordFieldValue.isNotEmpty() && confirmPassWordFieldValue.isNotEmpty() && nameFieldValue.isEmailValid()) {
                if (confirmPassWordFieldValue == passWordFieldValue) {
                    val users = Users(
                        userName = nameFieldValue,
                        userPass = passWordFieldValue
                    )
                    if (context.myAppPreferences.get(
                            StringConstants.savedUserConst,
                            ""
                        ) != users.userName
                    ) {
                        registrationViewModel.deleteAllData()
                    } else {
                        Toast.makeText(
                            context,
                            StringConstants.alreadyRegisteredUserMsg,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                    registrationViewModel.insertUser(users = users)
                    context.myAppPreferences[StringConstants.sessionConst] = true
                    context.myAppPreferences[StringConstants.savedUserConst] = users.userName

                    navController.navigate(StringConstants.homeRoute) {
                        popUpTo(StringConstants.loginRoute) {
                            inclusive = true
                        }
                    }

                } else {
                    Toast.makeText(
                        context,
                        StringConstants.passwordConfirmError,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(context, StringConstants.invalidEmailError,Toast.LENGTH_SHORT).show()
            }
        }, modifier = Modifier.width(220.dp)) {
            Text(text = "Submit", style = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp)),color = Color.White)
        }
    }
}