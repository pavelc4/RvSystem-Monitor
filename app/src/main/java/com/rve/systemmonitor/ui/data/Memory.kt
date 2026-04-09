package com.rve.systemmonitor.ui.data

data class RAM(val total: Double = 0.0, val available: Double = 0.0, val used: Double = 0.0, val usedPercentage: Double = 0.0)

data class ZRAM(
    val isActive: Boolean = false,
    val total: Double = 0.0,
    val available: Double = 0.0,
    val used: Double = 0.0,
    val usedPercentage: Double = 0.0,
)
