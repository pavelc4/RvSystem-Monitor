package com.rve.systemmonitor.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable data object Home : Route
    @Serializable data object CPU : Route
    @Serializable data object RAM : Route
    @Serializable data object Processes : Route
}
