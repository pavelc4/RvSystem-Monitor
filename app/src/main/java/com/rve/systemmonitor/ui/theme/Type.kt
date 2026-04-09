package com.rve.systemmonitor.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.rve.systemmonitor.R

val googleSansFontFamily =
    FontFamily(
        Font(R.font.google_sans_flex, weight = FontWeight.Medium),
        Font(R.font.google_sans_flex, weight = FontWeight.Normal),
        Font(R.font.google_sans_flex, weight = FontWeight.Bold),
        Font(R.font.google_sans_flex, weight = FontWeight.SemiBold),
    )

val defaultTypography = Typography()
val appTypography =
    Typography(
        displayLarge = defaultTypography.displayLarge.copy(fontFamily = googleSansFontFamily),
        displayMedium = defaultTypography.displayMedium.copy(fontFamily = googleSansFontFamily),
        displaySmall = defaultTypography.displaySmall.copy(fontFamily = googleSansFontFamily),
        headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = googleSansFontFamily),
        headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = googleSansFontFamily),
        headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = googleSansFontFamily),
        titleLarge = defaultTypography.titleLarge.copy(fontFamily = googleSansFontFamily),
        titleMedium = defaultTypography.titleMedium.copy(fontFamily = googleSansFontFamily),
        titleSmall = defaultTypography.titleSmall.copy(fontFamily = googleSansFontFamily),
        bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = googleSansFontFamily),
        bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = googleSansFontFamily),
        bodySmall = defaultTypography.bodySmall.copy(fontFamily = googleSansFontFamily),
        labelLarge = defaultTypography.labelLarge.copy(fontFamily = googleSansFontFamily),
        labelMedium = defaultTypography.labelMedium.copy(fontFamily = googleSansFontFamily),
        labelSmall = defaultTypography.labelSmall.copy(fontFamily = googleSansFontFamily),
    )
