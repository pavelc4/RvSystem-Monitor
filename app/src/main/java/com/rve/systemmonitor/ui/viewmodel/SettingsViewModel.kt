package com.rve.systemmonitor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rve.systemmonitor.domain.repository.SettingsRepository
import com.rve.systemmonitor.utils.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsRepository: SettingsRepository) : ViewModel() {

    val themeMode: StateFlow<ThemeMode> = settingsRepository.themeMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ThemeMode.SYSTEM,
        )

    val hapticFeedbackEnabled: StateFlow<Boolean> = settingsRepository.hapticFeedbackEnabled
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true,
        )

    val cpuRefreshDelay: StateFlow<Long> = settingsRepository.cpuRefreshDelay
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 3000L,
        )

    val memoryRefreshDelay: StateFlow<Long> = settingsRepository.memoryRefreshDelay
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 3000L,
        )

    val batteryRefreshDelay: StateFlow<Long> = settingsRepository.batteryRefreshDelay
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 1000L,
        )

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            settingsRepository.setThemeMode(mode)
        }
    }

    fun setHapticFeedbackEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setHapticFeedbackEnabled(enabled)
        }
    }

    fun setCpuRefreshDelay(delayMillis: Long) {
        viewModelScope.launch {
            settingsRepository.setCpuRefreshDelay(delayMillis)
        }
    }

    fun setMemoryRefreshDelay(delayMillis: Long) {
        viewModelScope.launch {
            settingsRepository.setMemoryRefreshDelay(delayMillis)
        }
    }

    fun setBatteryRefreshDelay(delayMillis: Long) {
        viewModelScope.launch {
            settingsRepository.setBatteryRefreshDelay(delayMillis)
        }
    }
}
