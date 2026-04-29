package com.rve.systemmonitor.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.IntOffset

const val TRANSITION_DURATION = 350
private val TRANSITION_EASING = FastOutSlowInEasing

fun enterTransition() = slideInHorizontally(
    animationSpec = tween(TRANSITION_DURATION, easing = TRANSITION_EASING),
    initialOffsetX = { it },
)

fun exitTransition() = slideOutHorizontally(
    animationSpec = tween(TRANSITION_DURATION, easing = TRANSITION_EASING),
    targetOffsetX = { -it / 3 },
) + fadeOut(
    animationSpec = tween(TRANSITION_DURATION, easing = TRANSITION_EASING),
)

fun popEnterTransition() = slideInHorizontally(
    animationSpec = tween(TRANSITION_DURATION, easing = TRANSITION_EASING),
    initialOffsetX = { -it / 3 },
) + scaleIn(
    animationSpec = tween(TRANSITION_DURATION, easing = TRANSITION_EASING),
    initialScale = 0.9f,
)

fun popExitTransition() = slideOutHorizontally(
    animationSpec = tween(TRANSITION_DURATION, easing = TRANSITION_EASING),
    targetOffsetX = { it },
) + scaleOut(
    animationSpec = tween(TRANSITION_DURATION, easing = TRANSITION_EASING),
    targetScale = 0.75f,
    transformOrigin = TransformOrigin(0.5f, 0.5f),
)

enum class MainRootDirection {
    FORWARD,
    BACKWARD,
}

private const val BOTTOM_NAV_TRANSITION_DURATION = 250

private val MAIN_ROOT_TRANSITION_SPEC =
    tween<IntOffset>(durationMillis = BOTTOM_NAV_TRANSITION_DURATION, easing = FastOutSlowInEasing)

private val MAIN_ROOT_FADE_SPEC =
    tween<Float>(durationMillis = BOTTOM_NAV_TRANSITION_DURATION, easing = FastOutSlowInEasing)

fun mainRootDirection(fromRoute: String?, toRoute: String?): MainRootDirection? {
    val fromIndex = mainRootRouteIndex(fromRoute) ?: return null
    val toIndex = mainRootRouteIndex(toRoute) ?: return null
    if (fromIndex == toIndex) return null
    return if (toIndex > fromIndex) MainRootDirection.FORWARD else MainRootDirection.BACKWARD
}

private fun mainRootRouteIndex(route: String?): Int? = when {
    route?.contains("Main") == true -> 0
    else -> null
}

fun mainRootEnterTransition(fromRoute: String?, toRoute: String?, fallback: EnterTransition): EnterTransition =
    when (mainRootDirection(fromRoute, toRoute)) {
        MainRootDirection.FORWARD -> {
            slideInHorizontally(
                animationSpec = MAIN_ROOT_TRANSITION_SPEC,
                initialOffsetX = { it },
            ) + fadeIn(animationSpec = MAIN_ROOT_FADE_SPEC)
        }

        MainRootDirection.BACKWARD -> {
            slideInHorizontally(
                animationSpec = MAIN_ROOT_TRANSITION_SPEC,
                initialOffsetX = { -it },
            ) + fadeIn(animationSpec = MAIN_ROOT_FADE_SPEC)
        }

        null -> fallback
    }

fun mainRootExitTransition(fromRoute: String?, toRoute: String?, fallback: ExitTransition): ExitTransition =
    when (mainRootDirection(fromRoute, toRoute)) {
        MainRootDirection.FORWARD -> {
            slideOutHorizontally(
                animationSpec = MAIN_ROOT_TRANSITION_SPEC,
                targetOffsetX = { -it },
            ) + fadeOut(animationSpec = MAIN_ROOT_FADE_SPEC)
        }

        MainRootDirection.BACKWARD -> {
            slideOutHorizontally(
                animationSpec = MAIN_ROOT_TRANSITION_SPEC,
                targetOffsetX = { it },
            ) + fadeOut(animationSpec = MAIN_ROOT_FADE_SPEC)
        }

        null -> fallback
    }
