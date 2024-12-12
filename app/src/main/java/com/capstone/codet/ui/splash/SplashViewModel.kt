package com.capstone.codet.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.codet.data.MainRepository
import com.capstone.codet.data.model.User

class SplashViewModel(private val repository: MainRepository):ViewModel() {

    fun getSession(): LiveData<User> {
        return repository.getSession().asLiveData()
    }

}