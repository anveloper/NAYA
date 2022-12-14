package com.youme.naya.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val PrimaryBlue = Color(0xFF2C67FF)
val PrimaryDark = Color(0xFF122045)
val PrimaryLight = Color(0xFFF4F7F9)
val SecondaryLightBlue = Color(0xFFDFE6FB)
val SecondaryMediumBlue = Color(0xFF5C8AFF)
val SecondarySystemBlue = Color(0xFF0891F2)
val SecondaryBasicBlue = Color(0xFF055EEA)
val SecondaryDarkBlue = Color(0xFF044BBB)
val SystemLightRed = Color(0xFFFFF2F9)
val SystemOrange = Color(0xFFF57227)
val SystemPink = Color(0xFFFE647C)
val SystemRed = Color(0xFFFE3D00)
val SystemLightYellow = Color(0xFFFFF9DE)
val SystemYellow = Color(0xFFFEBA00)
val SystemLightGreen = Color(0xFFEBF9F1)
val SystemGreen = Color(0xFF3BC171)
val SystemLightPurple = Color(0xFFF2EFFF)
val SystemPurple = Color(0xFF7B61FE)
val NeutralWhite = Color(0xFFFEFEFE)
val NeutralLightness = Color(0xFFF2F5F9)
val NeutralLight = Color(0xFFCED3D6)
val NeutralMedium = Color(0xFFBDC5CA)
val NeutralGray = Color(0xFFA1ACB3)
val NeutralMetal = Color(0xFF88929F)
val NeutralDarkGray = Color(0xFF444657)
val NeutralWhiteTrans = Color(0xFAFEFEFE)
val NeutralGrayTrans = Color(0xFA1ACB3)

val PrimaryGradientBrush = Brush.verticalGradient(
    listOf(
        Color(0xFF055EEA),
        Color(0xFF0891F2)
    )
)
val PrimaryGradientBrushH = Brush.horizontalGradient(
    listOf(
        Color(0xFF055EEA),
        Color(0xFF0891F2)
    )
)

val SecondaryGradientBrush = Brush.verticalGradient(
    colors = listOf(
        SecondaryBasicBlue,
        SecondarySystemBlue
    )
)

val SystemRedGradientBrush = Brush.verticalGradient(
    listOf(
        SystemRed,
        SystemPink
    )
)