package com.youme.naya.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.repository.ScheduleRepository
import com.youme.naya.ui.theme.SecondarySystemBlue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ScheduleMainViewModel @Inject constructor(
    private val repository: ScheduleRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val _schedules = mutableStateOf(emptyList<Schedule>())
    val schedules: State<List<Schedule>> = _schedules

    private val _schedulesAll = mutableStateOf(emptyList<Schedule>())
    val schedulesAll: State<List<Schedule>> = _schedulesAll


    private val _selectedDate = mutableStateOf(
        Clock.System.todayIn(TimeZone.currentSystemDefault()).toString())
    val selectedDate: State<String> = _selectedDate

    private var currentScheduleId: Int? = null

    private var recentlyDeletedSchedule: Schedule? = null

    private val _title = mutableStateOf(TextFieldState())
    var title: State<TextFieldState> = _title

    private val _description = mutableStateOf(TextFieldState())
    var description: State<TextFieldState> = _description

    private val _address = mutableStateOf(TextFieldState())
    var address: State<TextFieldState> = _address

    private val _color = mutableStateOf(SecondarySystemBlue.toArgb())
    var color: State<Int> = _color

    private val _isOnAlarm = mutableStateOf(false)
    var isOnAlarm: State<Boolean> = _isOnAlarm

    private val _startTime = mutableStateOf("01 : 00 PM")
    var startTime: State<String> = _startTime

    private val _endTime = mutableStateOf("12 : 00 PM")
    var endTime: State<String> = _endTime

    private val _alarmTime = mutableStateOf("시작 시간")
    var alarmTime: State<String> = _alarmTime

    private val _isDone = mutableStateOf(false)
    val isDone: State<Boolean> = _isDone

    init {
        viewModelScope.launch {
        repository.getSchedulesByDate(selectedDate.value)
            .collect { schedules ->
                _schedules.value = schedules
            }
            _schedules.value = schedules.value.sortedBy { it.startTime }
        }
        viewModelScope.launch {
            repository.getSchedules()
                .collect { schedules ->
                    _schedulesAll.value = schedules
                }

        }

        savedStateHandle.get<Int>("scheduleId")?.let { scheduleId ->
            if (scheduleId != -1) {
                viewModelScope.launch {
                    repository.getScheduleById(scheduleId)?.also { schedule ->
                        currentScheduleId = schedule.scheduleId
                        _title.value = title.value.copy(
                            text = schedule.title
                        )
                        _description.value = description.value.copy(
                            text = schedule.description
                        )
                        _address.value = schedule.address?.let {
                            address.value.copy(
                                text = it
                            )
                        }!!
                        _color.value = schedule.color
                        _isDone.value = schedule.isDone
                        _isOnAlarm.value = schedule.isOnAlarm
                        _startTime.value = schedule.startTime.toString()
                        _endTime.value = schedule.endTime.toString()
                        _alarmTime.value = schedule.alarmTime
                        _selectedDate.value = schedule.scheduleDate
                    }
                }
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
                .collect { schedules ->
                    _schedules.value = schedules
                }
            _schedules.value = schedules.value.sortedBy { it.startTime }
        }
    }

    fun onDoneChange(scheduleId: Int, isDone: Boolean) {
        viewModelScope.launch {
            repository.getScheduleById(scheduleId)?.also { schedule ->
            repository.insertSchedule(
                schedule.copy(
                    isDone = isDone
                )
            )}
        }
    }

    fun onTitleChange(EnteredTitle: String) {
        _title.value = title.value.copy(
            text = EnteredTitle
        )
    }

    fun onDescriptionChange(EnteredDescription: String) {
        _description.value = description.value.copy(
            text = EnteredDescription
        )
    }

    fun onAddressChange(EnteredAddress: String) {
        _address.value = address.value.copy(
            text = EnteredAddress
        )
    }

    fun onColorChange(EnteredColor: Int) {
        _color.value = EnteredColor
    }

    fun onAlarmChange() {
        _isOnAlarm.value = !isOnAlarm.value
    }

    fun alarmTimeChange(alarmTime: String) {
        _alarmTime.value = alarmTime
    }

    fun onStartTimeChange(time: String) {
        _startTime.value = time
    }

    fun onEndTimeChange(time: String) {
        _endTime.value = time
    }


    fun deleteSchedule(scheduleId: Int?) {
        viewModelScope.launch {
            if (scheduleId != null) {
                val schedule =  repository.getScheduleById(scheduleId)
                if (schedule != null) {
                    recentlyDeletedSchedule = schedule
                    repository.deleteSchedule(schedule)
                }
            }
        }
    }

    fun restoreSchedule() {
        viewModelScope.launch {
            repository.insertSchedule(

                recentlyDeletedSchedule ?: return@launch)
            recentlyDeletedSchedule = null
        }
    }


    fun insertSchedule(schedule: Schedule? = null, selectedDate: String) {
        viewModelScope.launch {
            repository.insertSchedule(
                Schedule(
                    title = title.value.text,
                    description = description.value.text,
                    isDone = isDone.value,
                    scheduleId = currentScheduleId,
                    address = address.value.text,
                    scheduleDate = selectedDate,
                    color = color.value,
                    isOnAlarm = isOnAlarm.value,
                    startTime = startTime.value,
                    endTime = endTime.value,
                )
            )
        }
    }

}