package com.rve.systemmonitor.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.composables.icons.materialsymbols.rounded.R.drawable.materialsymbols_ic_home_rounded
import com.composables.icons.materialsymbols.rounded.R.drawable.materialsymbols_ic_list_rounded
import com.composables.icons.materialsymbols.rounded.R.drawable.materialsymbols_ic_memory_alt_rounded
import com.composables.icons.materialsymbols.rounded.R.drawable.materialsymbols_ic_memory_rounded
import com.composables.icons.materialsymbols.roundedfilled.R.drawable.materialsymbols_ic_home_rounded_filled
import com.composables.icons.materialsymbols.roundedfilled.R.drawable.materialsymbols_ic_list_rounded_filled
import com.composables.icons.materialsymbols.roundedfilled.R.drawable.materialsymbols_ic_memory_alt_rounded_filled
import com.composables.icons.materialsymbols.roundedfilled.R.drawable.materialsymbols_ic_memory_rounded_filled
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy

object BottomNavBar {
    data class NavItem(val label: String, val iconUnselected: Int, val iconSelected: Int, val route: Route)

    @Composable
    fun BottomNavigationBar(navController: NavController, backdrop: Backdrop, modifier: Modifier = Modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val backgroundColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)

        val items = listOf(
            NavItem(
                label = "Home",
                iconUnselected = materialsymbols_ic_home_rounded,
                iconSelected = materialsymbols_ic_home_rounded_filled,
                route = Route.Home,
            ),
            NavItem(
                label = "CPU",
                iconUnselected = materialsymbols_ic_memory_rounded,
                iconSelected = materialsymbols_ic_memory_rounded_filled,
                route = Route.CPU,
            ),
            NavItem(
                label = "RAM",
                iconUnselected = materialsymbols_ic_memory_alt_rounded,
                iconSelected = materialsymbols_ic_memory_alt_rounded_filled,
                route = Route.RAM,
            ),
            NavItem(
                label = "Processes",
                iconUnselected = materialsymbols_ic_list_rounded,
                iconSelected = materialsymbols_ic_list_rounded_filled,
                route = Route.Processes,
            ),
        )

        Box(
            modifier = modifier
                .clip(CircleShape)
                .drawBackdrop(
                    backdrop = backdrop,
                    shape = { CircleShape },
                    effects = {
                        vibrancy()
                        blur(4f.dp.toPx())
                        lens(16f.dp.toPx(), 32f.dp.toPx())
                    },
                    onDrawSurface = { drawRect(backgroundColor) },
                ),
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items.forEach { item ->
                    val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true

                    BottomNavItem(
                        backdrop = backdrop,
                        item = item,
                        isSelected = isSelected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        }
    }

    @Composable
    private fun BottomNavItem(backdrop: Backdrop, item: NavItem, isSelected: Boolean, onClick: () -> Unit) {
        val backgroundColor by animateColorAsState(
            targetValue = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else Color.Transparent,
            label = "Background Color Animation",
        )

        val contentColor by animateColorAsState(
            targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
            label = "Content Color Animation",
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = onClick)
                .animateContentSize(
                    animationSpec = MaterialTheme.motionScheme.slowSpatialSpec(),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .matchParentSize()
                    .background(backgroundColor),
            )
            AnimatedVisibility(
                visible = isSelected,
                enter = fadeIn(
                    animationSpec = MaterialTheme.motionScheme.slowEffectsSpec(),
                ),
                exit = fadeOut(
                    animationSpec = MaterialTheme.motionScheme.slowEffectsSpec(),
                ),
                modifier = Modifier.matchParentSize(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .drawBackdrop(
                            backdrop = backdrop,
                            shape = { CircleShape },
                            effects = {
                                vibrancy()
                                blur(4f.dp.toPx())
                                lens(16f.dp.toPx(), 32f.dp.toPx())
                            },
                            onDrawSurface = { drawRect(backgroundColor) },
                        ),
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Crossfade(
                    targetState = isSelected,
                    animationSpec = MaterialTheme.motionScheme.slowEffectsSpec(),
                    label = "Icon Crossfade Animation",
                ) {
                    Icon(
                        painter = painterResource(if (it) item.iconSelected else item.iconUnselected),
                        contentDescription = item.label,
                        tint = contentColor,
                    )
                }

                if (isSelected) {
                    Text(
                        text = item.label,
                        color = contentColor,
                        maxLines = 1,
                        softWrap = true,
                    )
                }
            }
        }
    }
}
