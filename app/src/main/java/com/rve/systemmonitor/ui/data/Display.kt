package com.rve.systemmonitor.ui.data

data class Display(
    val resolution: String = "unknown",
    val refreshRate: Int = 0,
    val densityDpi: Int = 0,
    val screenSizeInches: Double = 0.0,
)
