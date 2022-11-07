package com.youme.naya.widgets.calendar.customCalendar.component.day.config

import androidx.compose.ui.graphics.Color
import com.youme.naya.ui.theme.NeutralWhite
import com.youme.naya.ui.theme.PrimaryBlue
import com.youme.naya.ui.theme.PrimaryDark

data class CustomCalendarDayColors(
    val textColor: Color, // Default Text Color
    val selectedTextColor: Color, // Selected Text Color
)

object CustomCalendarDayDefaultColors {

    fun defaultColors() = CustomCalendarDayColors(PrimaryDark, NeutralWhite)
}
