package com.youme.naya.schedule

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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleEditViewModel @Inject constructor(
    private val repository: ScheduleRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var currentScheduleId: Int? = null

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

    private var recentlyDeletedSchedule: Schedule? = null

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _isDone = mutableStateOf(false)
    val isDone: State<Boolean> = _isDone

    init {
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
                    }
                }
            }
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

    suspend fun deleteSchedule(schedule: Schedule) {
        repository.deleteSchedule(schedule)
    }

    fun restoreSchedule() {
      viewModelScope.launch {
            repository.insertSchedule(
                recentlyDeletedSchedule ?: return@launch)
            recentlyDeletedSchedule = null
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
            sendUiEvent(UiEvent.PopBackStack)
        }
    }

    fun onEditClick(schedule: Schedule) {
        sendUiEvent(UiEvent.Navigate(Screen.ScheduleEditScreen.passId(schedule.scheduleId)))
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}