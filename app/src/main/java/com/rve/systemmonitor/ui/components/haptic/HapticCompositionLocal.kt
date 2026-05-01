package com.rve.systemmonitor.ui.components.haptic

import androidx.compose.runtime.staticCompositionLocalOf
import com.rve.systemmonitor.utils.VibrationIntensity

val LocalHapticEnabled = staticCompositionLocalOf { true }
val LocalVibrationIntensity = staticCompositionLocalOf { VibrationIntensity.LIGHT }
