package com.capstone.codet.data.di

import android.content.Context
import com.capstone.codet.data.MainRepository
import com.capstone.codet.data.api.ml.MLApiConfig
import com.capstone.codet.data.api.main.ApiConfig
import com.capstone.codet.data.preference.SettingPreference
import com.capstone.codet.data.preference.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


object Injection {


    fun provideRepository(context: Context): MainRepository {
        val mlApiService = MLApiConfig.provideApiService()
        val preference = SettingPreference.getInstance(context.dataStore)

        val user = runBlocking { preference.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return MainRepository(mlApiService, preference, apiService)
    }


}