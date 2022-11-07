package com.youme.naya.widgets.calendar.customCalendar.component.text.config

import androidx.compose.ui.graphics.Color
import com.youme.naya.ui.theme.PrimaryDark

data class CustomCalendarTextColor(
    val textColor: Color
)

private val TitleTextColor = PrimaryDark

internal object CustomCalendarTextColorDefaults {

    fun customCalendarTitleTextColor(color: Color = TitleTextColor) = CustomCalendarTextColor(
        textColor = color
    )

    fun customCalendarNormalTextColor(color: Color = TitleTextColor) = CustomCalendarTextColor(
        textColor = color
    )
}
