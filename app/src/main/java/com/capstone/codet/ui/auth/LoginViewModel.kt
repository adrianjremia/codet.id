package com.capstone.codet.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.codet.data.MainRepository
import com.capstone.codet.data.model.User
import com.capstone.codet.data.response.LoginResponse
import kotlinx.coroutines.launch
import com.capstone.codet.data.utils.Result

class LoginViewModel(private val repository: MainRepository):ViewModel() {

    fun login(email: String, pass: String): LiveData<Result<LoginResponse>> =
        repository.login(email, pass)


    fun saveSession(user: User) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }


}