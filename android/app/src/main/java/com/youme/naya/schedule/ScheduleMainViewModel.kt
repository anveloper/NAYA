package com.youme.naya.schedule

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.repository.ScheduleRepository
import com.youme.naya.schedule.Screen
import com.youme.naya.schedule.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject

@HiltViewModel
class ScheduleMainViewModel @Inject constructor(
    private val repository: ScheduleRepository
): ViewModel() {

    private val _schedules = mutableStateOf(emptyList<Schedule>())
    val schedules: State<List<Schedule>> = _schedules

    private val _isDone = mutableStateOf(false)
    val isDone: State<Boolean> = _isDone

    private val _selectedDate = mutableStateOf(
        Clock.System.todayIn(TimeZone.currentSystemDefault()).toString())
    val selectedDate: State<String> = _selectedDate

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            repository.getSchedulesByDate(selectedDate.value)
                .collect { schedules ->
                    _schedules.value = schedules
                }
        }
    }

    fun getSelectedDate(
        date: String
    ) {
        _selectedDate.value = date
    }

    fun getScheduleList(date: String) {
        viewModelScope.launch {
            repository.getSchedulesByDate(date)
                .onEach { schedules ->
                    _schedules.value = schedules
                }
        }
    }

    fun onScheduleClick(schedule: Schedule) {
        sendUiEvent(UiEvent.Navigate(Screen.ScheduleDetail.passId(schedule.scheduleId)))
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

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}