package com.youme.naya.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
<<<<<<< HEAD
=======
import com.youme.naya.R


// font 설정
val pico = FontFamily(
    Font(R.font.pico_black, weight = FontWeight.Black),
)

val fonts = FontFamily(
    Font(R.font.pretendard_black, weight = FontWeight.Black),
    Font(R.font.pretendard_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.pretendard_bold, weight = FontWeight.Bold),
    Font(R.font.pretendard_semibold, weight = FontWeight.SemiBold),
    Font(R.font.pretendard_medium, weight = FontWeight.Medium),
    Font(R.font.pretendard_regular, weight = FontWeight.Normal),
    Font(R.font.pretendard_thin, weight = FontWeight.Thin),
    Font(R.font.pretendard_light, weight = FontWeight.Light),
    Font(R.font.pretendard_extralight, weight = FontWeight.ExtraLight),
)
>>>>>>> android

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)