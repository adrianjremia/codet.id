package com.capstone.codet.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.capstone.codet.data.preference.SettingPreference

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
object Injection {

    fun provideSettingPreference(context: Context): SettingPreference {
        return SettingPreference.getInstance(context.dataStore)
    }

}