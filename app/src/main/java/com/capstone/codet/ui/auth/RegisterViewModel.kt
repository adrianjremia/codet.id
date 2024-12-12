package com.capstone.codet.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.codet.data.MainRepository
import com.capstone.codet.data.response.RegisterResponse
import com.capstone.codet.data.utils.Result

class RegisterViewModel(private val repository: MainRepository):ViewModel() {


    fun register(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> =
        repository.register(name, email, password)
}