package com.rve.systemmonitor.ui.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

private fun NavController.isReadyForNavigation(): Boolean {
    return runCatching {
        currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
    }.getOrDefault(false)
}

fun NavController.navigateSafely(route: Route): Boolean {
    if (!isReadyForNavigation()) return false
    navigate(route) {
        launchSingleTop = true
    }
    return true
}

fun NavController.navigateSafely(route: Route, builder: NavOptionsBuilder.() -> Unit): Boolean {
    if (!isReadyForNavigation()) return false
    navigate(route) {
        launchSingleTop = true
        builder()
    }
    return true
}

fun NavController.navigateToTopLevelSafely(route: Route): Boolean {
    val startDestinationId = runCatching { graph.startDestinationId }.getOrNull() ?: return false
    navigate(route) {
        popUpTo(startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
    return true
}
