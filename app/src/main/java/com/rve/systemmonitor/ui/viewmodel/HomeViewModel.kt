package com.rve.systemmonitor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rve.systemmonitor.domain.repository.SystemInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val systemInfoRepository: SystemInfoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                device = systemInfoRepository.getDeviceInfo(),
                os = systemInfoRepository.getOSInfo(),
                display = systemInfoRepository.getDisplayInfo(),
                cpu = systemInfoRepository.getCpuInfo(),
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val gpuInfo = systemInfoRepository.getGpuInfo()
            systemInfoRepository.getMemoryInfo()
                .flowOn(Dispatchers.IO)
                .collect { (ram, zram) ->
                    _uiState.update {
                        it.copy(
                            gpu = gpuInfo,
                            ram = ram,
                            zram = zram,
                        )
                    }
                }
        }
    }
}
