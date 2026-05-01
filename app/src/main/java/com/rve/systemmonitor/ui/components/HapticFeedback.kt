package com.rve.systemmonitor.ui.components

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import com.rve.systemmonitor.utils.VibrationIntensity

/**
 * Performs custom haptic feedback using the system vibrator.
 */
private fun performCustomHapticFeedback(context: Context, intensity: VibrationIntensity) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    if (!vibrator.hasVibrator()) return

    val (duration, amplitude) = when (intensity) {
        VibrationIntensity.LIGHT -> 15L to 100
        VibrationIntensity.MEDIUM -> 25L to 180
        VibrationIntensity.STRONG -> 40L to 255
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(duration)
    }
}

/**
 * A helper to provide consistent haptic feedback when an onClick event occurs.
 */
@Composable
fun rememberHapticOnClick(onClick: () -> Unit): () -> Unit {
    val context = LocalContext.current
    val hapticEnabled = LocalHapticEnabled.current
    val intensity = LocalVibrationIntensity.current
    return remember(onClick, hapticEnabled, intensity, context) {
        {
            if (hapticEnabled) {
                performCustomHapticFeedback(context, intensity)
            }
            onClick()
        }
    }
}

/**
 * A helper to provide haptic feedback when a value changes (e.g., Slider steps).
 */
@Composable
fun <T> rememberHapticOnValueChange(onValueChange: (T) -> Unit): (T) -> Unit {
    val context = LocalContext.current
    val hapticEnabled = LocalHapticEnabled.current
    val intensity = LocalVibrationIntensity.current
    return remember(onValueChange, hapticEnabled, intensity, context) {
        { newValue ->
            if (hapticEnabled) {
                performCustomHapticFeedback(context, intensity)
            }
            onValueChange(newValue)
        }
    }
}

/**
 * A custom modifier that provides haptic feedback along with the standard clickable behavior.
 */
fun Modifier.hapticClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    indication: androidx.compose.foundation.Indication? = null,
    interactionSource: MutableInteractionSource? = null,
    onClick: () -> Unit,
): Modifier = composed {
    val context = LocalContext.current
    val hapticEnabled = LocalHapticEnabled.current
    val intensity = LocalVibrationIntensity.current
    val hapticOnClick = remember(onClick, hapticEnabled, intensity, context) {
        {
            if (hapticEnabled) {
                performCustomHapticFeedback(context, intensity)
            }
            onClick()
        }
    }

    this.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        indication = indication ?: ripple(),
        interactionSource = interactionSource ?: remember { MutableInteractionSource() },
        onClick = hapticOnClick,
    )
}
