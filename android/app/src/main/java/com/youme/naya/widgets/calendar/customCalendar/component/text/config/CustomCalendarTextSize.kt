package com.youme.naya.widgets.calendar.customCalendar.component.text.config

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

sealed class CustomCalendarTextSize(val size: TextUnit) {
    object Title : CustomCalendarTextSize(32.sp)
    object SubTitle : CustomCalendarTextSize(24.sp)
    object Normal : CustomCalendarTextSize(18.sp)
    object Small : CustomCalendarTextSize(15.sp)
    data class Custom(val textUnit: TextUnit) : CustomCalendarTextSize(textUnit)
}