package com.youme.naya.widgets.calendar.customCalendar.model

sealed interface CustomCalendarType {
    object Basic : CustomCalendarType
    data class Fold(val showWeekDays: Boolean = true) : CustomCalendarType
}