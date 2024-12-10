package com.capstone.codet.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.capstone.codet.data.MainRepository
import com.capstone.codet.data.api.ApiConfig
import com.capstone.codet.data.preference.SettingPreference

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
object Injection {


    fun provideRepository(context: Context): MainRepository {
        val apiService = ApiConfig.provideApiService()
        val preference = SettingPreference.getInstance(context.dataStore)
        return MainRepository(apiService, preference)
    }


    fun provideSettingPreference(context: Context): SettingPreference {
        return SettingPreference.getInstance(context.dataStore)
    }

}