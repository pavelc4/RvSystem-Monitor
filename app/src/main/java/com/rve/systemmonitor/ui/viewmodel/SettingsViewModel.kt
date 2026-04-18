package com.rve.systemmonitor.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rve.systemmonitor.utils.SettingsPreferences
import com.rve.systemmonitor.utils.ThemeMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsPreferences = SettingsPreferences(application)

    val themeMode: StateFlow<ThemeMode> = settingsPreferences.themeModeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ThemeMode.SYSTEM,
        )

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            settingsPreferences.saveThemeMode(mode)
        }
    }
}
