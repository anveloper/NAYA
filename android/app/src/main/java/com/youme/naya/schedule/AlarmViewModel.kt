package com.youme.naya.schedule

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youme.naya.database.entity.Alarm
import com.youme.naya.database.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val repository: ScheduleRepository) : ViewModel()  {

    private val _alarms = mutableStateOf(emptyList<Alarm>())
    val alarms: MutableState<List<Alarm>> = _alarms

    init {
        viewModelScope.launch {
            repository.getAlarms()
                .collect { alarms ->
                    _alarms.value = alarms
                }
        }
    }

}