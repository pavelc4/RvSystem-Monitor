package com.rve.systemmonitor.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role

/**
 * A helper to provide consistent haptic feedback when an onClick event occurs.
 */
@Composable
fun rememberHapticOnClick(onClick: () -> Unit): () -> Unit {
    val haptic = LocalHapticFeedback.current
    val hapticEnabled = LocalHapticEnabled.current
    return remember(onClick, hapticEnabled) {
        {
            if (hapticEnabled) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
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
    val haptic = LocalHapticFeedback.current
    val hapticEnabled = LocalHapticEnabled.current
    return remember(onValueChange, hapticEnabled) {
        { newValue ->
            if (hapticEnabled) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
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
    val haptic = LocalHapticFeedback.current
    val hapticEnabled = LocalHapticEnabled.current
    val hapticOnClick = remember(onClick, hapticEnabled) {
        {
            if (hapticEnabled) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
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
