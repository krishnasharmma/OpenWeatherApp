package com.openweather.app.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openweather.app.data.db.entity.Users
import com.openweather.app.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(val databaseRepository: DatabaseRepository): ViewModel() {

    fun insertUser(users: Users){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                databaseRepository.insertUserData(users)
            }
        }

    }

    fun deleteAllData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                databaseRepository.deleteAllData()
            }
        }
    }
}