package com.youme.naya.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

//private val DarkColorPalette = darkColors(
//    primary = PrimaryBlue,
//    primaryVariant = PrimaryDark,
//    secondary = SecondaryDarkBlue
//    // 다크모드 고정
//)


//private val LightColorPalette = lightColors(
//    primary = PrimaryBlue,
//    primaryVariant = PrimaryDark,
//    secondary = SecondaryDarkBlue,
//    onPrimary = NeutralWhite,
//    onSecondary = NeutralDarkGray,
//    onBackground = NeutralDarkGray,
//    onSurface = NeutralDarkGray,
//    background = NeutralWhite,
//    surface = NeutralWhite,
//)

@Composable
fun AndroidTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }

    val colors = lightColors(
        primary = PrimaryBlue,
        primaryVariant = PrimaryDark,
        secondary = SecondaryDarkBlue,
        onPrimary = NeutralWhite,
        onSecondary = NeutralDarkGray,
        onBackground = NeutralDarkGray,
        onSurface = NeutralDarkGray,
        background = NeutralWhite,
        surface = NeutralWhite,
    )
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}