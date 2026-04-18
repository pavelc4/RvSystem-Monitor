@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package com.rve.systemmonitor.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.composables.icons.materialsymbols.roundedfilled.R.drawable.materialsymbols_ic_settings_rounded_filled

object AppBars {
    @Composable
    fun SimpleTopAppBar(
        title: String,
        subtitle: String,
        onNavigateToSettings: () -> Unit,
    ) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            subtitle = {
                Text(
                    text = subtitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            actions = {
                TooltipBox(
                    positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                        TooltipAnchorPosition.Left,
                    ),
                    tooltip = {
                        PlainTooltip {
                            Text("Settings")
                        }
                    },
                    state = rememberTooltipState(),
                ) {
                    IconButton(
                        onClick = onNavigateToSettings,
                    ) {
                        Icon(
                            painterResource(materialsymbols_ic_settings_rounded_filled),
                            contentDescription = "Settings",
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
            ),
        )
    }
}

@Composable
fun ExitUntilCollapsedMediumTopAppBar(
    title: String,
    onNavigateBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    MediumTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            TooltipBox(
                positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                    TooltipAnchorPosition.Right,
                ),
                tooltip = {
                    PlainTooltip {
                        Text("Back")
                    }
                },
                state = rememberTooltipState(),
            ) {
                IconButton(
                    onClick = onNavigateBack,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}
