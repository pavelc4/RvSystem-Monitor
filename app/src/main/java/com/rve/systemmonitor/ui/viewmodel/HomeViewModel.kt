package com.rve.systemmonitor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rve.systemmonitor.domain.repository.SystemInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val systemInfoRepository: SystemInfoRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = HomeUiState(
                device = systemInfoRepository.getDeviceInfo(),
                os = systemInfoRepository.getOSInfo(),
                display = systemInfoRepository.getDisplayInfo(),
                cpu = systemInfoRepository.getCpuInfo(),
                gpu = systemInfoRepository.getGpuInfo(),
            )
        }
    }
}
