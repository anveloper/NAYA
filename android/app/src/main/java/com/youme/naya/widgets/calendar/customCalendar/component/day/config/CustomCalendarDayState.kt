package com.youme.naya.widgets.calendar.customCalendar.component.day.config

sealed interface CustomCalendarDayState {
    object CustomCalendarDaySelected : CustomCalendarDayState
    object CustomCalendarDayDefault : CustomCalendarDayState
}