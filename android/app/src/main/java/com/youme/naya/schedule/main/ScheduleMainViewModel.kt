package com.youme.naya.schedule.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youme.naya.schedule.useCases.GetSchedules
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ScheduleMainViewModel @Inject constructor(
    getSchedules: GetSchedules
): ViewModel() {

    private val _state = mutableStateOf(ScheduleMainState())
    val state: State<ScheduleMainState> = _state

    init {
        getSchedules().onEach { schedules ->
            _state.value = state.value.copy(
                schedules = schedules
            )
        }.launchIn(viewModelScope)
    }
}