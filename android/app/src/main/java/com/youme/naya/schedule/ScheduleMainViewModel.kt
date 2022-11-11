package com.youme.naya.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.repository.ScheduleRepository
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ScheduleMainViewModel @Inject constructor(
    private val repository: ScheduleRepository
): ViewModel() {

    private val _schedules = mutableStateOf(emptyList<Schedule>())
    val schedules: State<List<Schedule>> = _schedules

    private val _allSchedules = mutableStateOf(emptyList<Schedule>())
    val allSchedules: State<List<Schedule>> = _allSchedules

    private val _isDone = mutableStateOf(false)
    val isDone: State<Boolean> = _isDone

    private val _selectedDate = mutableStateOf(
        Clock.System.todayIn(TimeZone.currentSystemDefault()).toString())
    val selectedDate: State<String> = _selectedDate

    private val _eventList = mutableStateOf(emptyList<CustomCalendarEvent>())
    val eventList: State<List<CustomCalendarEvent>> = _eventList


    init {
        viewModelScope.launch {
        repository.getSchedulesByDate(selectedDate.value)
            .collect { schedules ->
                _schedules.value = schedules
            }
        }
        viewModelScope.launch {
            repository.getSchedules()
                .collect { allSchedules ->
                    _allSchedules.value = allSchedules
                }
            allSchedules.value.forEach {
                _eventList.value += CustomCalendarEvent(LocalDate.parse(it.scheduleDate), it.title, it.description, it.color)
            }
        }
    }

    fun getSelectedDate(
        date: String
    ) {
        _selectedDate.value = date
    }

    fun getEventSchedule() {
        viewModelScope.launch {
            repository.getSchedules()
                .collect { allSchedules ->
                    _allSchedules.value = allSchedules
                }
//            allSchedules.value.forEach {
//                var list =  _eventList.value
//               list.add(CustomCalendarEvent(LocalDate.parse(it.scheduleDate), it.title, it.description, it.color))
//        }
        }
    }


    fun getScheduleList(date: String) {
        viewModelScope.launch {
            repository.getSchedulesByDate(date)
                .collect { schedules ->
                    _schedules.value = schedules
                }
        }
    }

    fun onDoneChange(schedule: Schedule, isDone: Boolean) {
        viewModelScope.launch {
            repository.insertSchedule(
                schedule.copy(
                    isDone = isDone
                )
            )
        }
    }

}