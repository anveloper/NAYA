package com.youme.naya.widgets.calendar.customCalendar.model

import kotlinx.datetime.LocalDate

@JvmInline
value class CustomCalendarDay(val localDate: LocalDate)

fun LocalDate.toCustomCalendarDay() = CustomCalendarDay(this)
