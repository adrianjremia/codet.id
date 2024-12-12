package com.capstone.codet.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.codet.data.di.Injection
import com.capstone.codet.data.preference.SettingPreference
import com.capstone.codet.ui.auth.LoginViewModel
import com.capstone.codet.ui.auth.RegisterViewModel
import com.capstone.codet.ui.history.HistoryViewModel
import com.capstone.codet.ui.home.HomeViewModel
import com.capstone.codet.ui.result.ResultViewModel
import com.capstone.codet.ui.scan.ScanViewModel
import com.capstone.codet.ui.setting.SettingViewModel
import com.capstone.codet.ui.splash.SplashViewModel

class ViewModelFactory( private val repository: MainRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(ScanViewModel::class.java) -> {
                ScanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
        }
    }

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                val repository = Injection.provideRepository(context)
                instance ?: ViewModelFactory(repository).also { instance = it }
            }
        }
    }


}