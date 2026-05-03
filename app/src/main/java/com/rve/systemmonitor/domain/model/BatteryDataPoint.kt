package com.rve.systemmonitor.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class BatteryDataPoint(val mA: Int, val status: String)
