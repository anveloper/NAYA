package com.youme.naya.widgets.calendar.customCalendar.component.text.config


data class CustomCalendarTextConfig(
    val customCalendarTextSize: CustomCalendarTextSize = CustomCalendarTextSize.Title
)

internal object CustomCalendarTextDefaults {

    fun customCalendarTitleTextConfig() = CustomCalendarTextConfig(
        customCalendarTextSize = CustomCalendarTextSize.Title
    )

    fun customCalendarSubTitleTextConfig() = CustomCalendarTextConfig(
        customCalendarTextSize = CustomCalendarTextSize.SubTitle
    )

    fun customCalendarNormalTextConfig() = CustomCalendarTextConfig(
        customCalendarTextSize = CustomCalendarTextSize.Normal
    )
}
