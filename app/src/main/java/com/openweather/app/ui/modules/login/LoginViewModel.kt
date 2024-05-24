package com.openweather.app.ui.modules.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openweather.app.data.db.entity.Users
import com.openweather.app.repository.DatabaseRepository
import com.openweather.app.utils.StringConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val databaseRepository: DatabaseRepository): ViewModel() {


    fun checkIfUserExist(users: Users, callback: (Int) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = determineLoginResult(users)
                    callback(result)
            }
        }
    }

    suspend fun determineLoginResult(users: Users): Int {
        val userList = databaseRepository.getUserList(users.userName).first().firstOrNull()

        return if (userList != null) {
            if (users.userPass == userList.userPass) {
                StringConstants.LOGIN_SUCCESS // LOGIN_SUCCESS
            } else {
                StringConstants.PASS_MISMATCH //PASSWORD_MISMATCH
            }
        } else {
            StringConstants.EMAIL_NOT_FOUND // USER_NOT_FOUND
        }
    }

    fun deleteData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseRepository.deleteAllData()
            }
        }
    }

}