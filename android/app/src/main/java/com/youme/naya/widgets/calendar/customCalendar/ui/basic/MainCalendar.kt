package com.youme.naya.widgets.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.youme.naya.ui.theme.NeutralWhite
import com.youme.naya.widgets.calendar.customCalendar.CustomCalendar
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarType

@Composable
private fun BottomShadow(alpha: Float = 0.1f, height: Dp = 8.dp) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(height)
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Black.copy(alpha = alpha),
                    Color.Transparent,
                )
            )
        )
    )
}

private val CornerShape =
    Modifier.clip(
        shape = RoundedCornerShape(
            bottomStartPercent = 10,
            bottomEndPercent = 10))

private val CalendarModifier =
    Modifier
        .background(color = NeutralWhite)
        .height(480.dp)
        .fillMaxWidth()
        .padding(top = 8.dp)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainCalendar() {
    Box (modifier = CornerShape) {
        BottomShadow(alpha = 1f, height = 490.dp)
        Box(modifier = CornerShape) {
            Box( modifier = CalendarModifier) {
//                CustomCalendar(customCalendarType = CustomCalendarType.Basic)
            }
        }
    }
}
