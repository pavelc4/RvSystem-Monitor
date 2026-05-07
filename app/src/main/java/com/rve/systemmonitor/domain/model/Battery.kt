package com.rve.systemmonitor.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Battery(
    val level: Int = 0,
    val health: String = "Unknown",
    val status: String = "Unknown",
    val technology: String = "Unknown",
    val voltage: Int = 0,
    val temperature: Float = 0f,
    val capacity: Double = 0.0,
    val cycleCount: Int = -1,
    val uptime: Long = 0L,
    val deepSleep: Long = 0L,
    val current: Int = 0,
    val wattage: Double = 0.0,
    val powerSource: String = "Battery",
)
