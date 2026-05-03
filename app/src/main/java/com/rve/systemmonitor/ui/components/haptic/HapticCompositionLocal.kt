package com.rve.systemmonitor.ui.components.haptic

import androidx.compose.runtime.staticCompositionLocalOf
import com.rve.systemmonitor.utils.VibrationIntensity

/**
 * CompositionLocal providing a boolean value to enable or disable haptic feedback globally.
 */
val LocalHapticEnabled = staticCompositionLocalOf { true }

/**
 * CompositionLocal providing the current [VibrationIntensity] for haptic feedback.
 */
val LocalVibrationIntensity = staticCompositionLocalOf { VibrationIntensity.LIGHT }
