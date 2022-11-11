package com.youme.naya.widgets.calendar.customCalendar.model

import kotlinx.datetime.LocalDate

data class CustomCalendarEvent(
    val date: LocalDate,
    val eventName: String,
    val eventDescription: String? = null,
    val eventColor: Int,
)
