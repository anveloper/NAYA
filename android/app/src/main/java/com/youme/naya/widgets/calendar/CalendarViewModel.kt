package com.youme.naya.widgets.calendar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class CalendarViewModel : ViewModel() {
    private val _selectedDate = mutableStateOf(
        Clock.System.todayIn(TimeZone.currentSystemDefault()))
    val selectedDate: State<LocalDate> = _selectedDate

    fun getSelectedDate(
        date: LocalDate
    ) {
        _selectedDate.value = date
    }
}