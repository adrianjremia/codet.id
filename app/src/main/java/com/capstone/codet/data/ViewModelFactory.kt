package com.capstone.codet.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.codet.data.di.Injection
import com.capstone.codet.data.preference.SettingPreference
import com.capstone.codet.ui.setting.SettingViewModel

class ViewModelFactory(private val pref: SettingPreference): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(pref) as T
            }


            else -> throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
        }
    }

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {

                val pref = Injection.provideSettingPreference(context)
                instance ?: ViewModelFactory(pref).also { instance = it }
            }
        }
    }


}