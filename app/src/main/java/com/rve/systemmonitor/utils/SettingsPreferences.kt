package com.rve.systemmonitor.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsPreferences(private val context: Context) {
    companion object {
        val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    }

    val themeModeFlow: Flow<ThemeMode> = context.dataStore.data
        .map { preferences ->
            val mode = preferences[THEME_MODE_KEY] ?: ThemeMode.SYSTEM.name
            ThemeMode.valueOf(mode)
        }

    suspend fun saveThemeMode(mode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = mode.name
        }
    }
}
