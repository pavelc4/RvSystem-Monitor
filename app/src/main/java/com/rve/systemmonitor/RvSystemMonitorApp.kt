package com.rve.systemmonitor

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.rve.systemmonitor.ui.navigation.BottomNavBar.BottomNavigationBar
import com.rve.systemmonitor.ui.navigation.Route
import com.rve.systemmonitor.ui.screens.CPUScreen
import com.rve.systemmonitor.ui.screens.HomeScreen
import com.rve.systemmonitor.ui.screens.ProcessesScreen
import com.rve.systemmonitor.ui.screens.RAMScreen

@Composable
fun RvSystemMonitorApp() {
    val navController = rememberNavController()

    val backgroundColor = MaterialTheme.colorScheme.background
    val backdrop = rememberLayerBackdrop {
        drawRect(backgroundColor)
        drawContent()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
    ) {
        val getTabIndex: (String?) -> Int = { route ->
            when {
                route == null -> 0
                route.contains("Home") -> 0
                route.contains("CPU") -> 1
                route.contains("RAM") -> 2
                route.contains("Processes") -> 3
                else -> 0
            }
        }

        NavHost(
            navController = navController,
            startDestination = Route.Home,
            modifier = Modifier.layerBackdrop(backdrop),
            enterTransition = {
                val initialIndex = getTabIndex(initialState.destination.route)
                val targetIndex = getTabIndex(targetState.destination.route)
                val isMovingRight = targetIndex > initialIndex

                slideInHorizontally(
                    initialOffsetX = { fullWidth -> if (isMovingRight) fullWidth else -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            },
            exitTransition = {
                val initialIndex = getTabIndex(initialState.destination.route)
                val targetIndex = getTabIndex(targetState.destination.route)
                val isMovingRight = targetIndex > initialIndex

                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> if (isMovingRight) -fullWidth else fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            },
            popEnterTransition = {
                val initialIndex = getTabIndex(initialState.destination.route)
                val targetIndex = getTabIndex(targetState.destination.route)
                val isMovingRight = targetIndex > initialIndex

                slideInHorizontally(
                    initialOffsetX = { fullWidth -> if (isMovingRight) fullWidth else -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            },
            popExitTransition = {
                val initialIndex = getTabIndex(initialState.destination.route)
                val targetIndex = getTabIndex(targetState.destination.route)
                val isMovingRight = targetIndex > initialIndex

                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> if (isMovingRight) -fullWidth else fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            }
        ) {
            composable<Route.Home> {
                HomeScreen()
            }
            composable<Route.CPU> {
                BackHandler { navController.popBackStack() }
                CPUScreen()
            }
            composable<Route.RAM> {
                BackHandler { navController.popBackStack() }
                RAMScreen()
            }
            composable<Route.Processes> {
                BackHandler { navController.popBackStack() }
                ProcessesScreen()
            }
        }

        BottomNavigationBar(
            navController = navController,
            backdrop = backdrop,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .windowInsetsPadding(WindowInsets.navigationBars),
        )
    }
}
