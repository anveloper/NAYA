package com.youme.naya.database.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    ) : ViewModel() {

    private val _schedules = mutableStateOf(emptyList<Schedule>())
    val schedules: State<List<Schedule>> = _schedules

    private var recentlyDeletedSchedule: Schedule? = null


    init {
        viewModelScope.launch {
            scheduleRepository.getSchedules()
                .collect { schedules ->
                    _schedules.value = schedules
                }
        }
    }

    fun insertSchedule(title: String, color: Int) {
        viewModelScope.launch {
            scheduleRepository.insertSchedule(Schedule(title = title, color = color))
        }
    }

    // Done 상태 변화
    fun toggle(scheduleId: Int) {
        val schedule = _schedules.value.find { schedule -> schedule.scheduleId == scheduleId}
        schedule?.let {
            viewModelScope.launch {
                scheduleRepository.updateSchedule(schedule.copy(isDone = !it.isDone).apply {
                    this.scheduleId = it.scheduleId
                })
            }
        }
    }
    
    // 삭제
    fun delete(scheduleId: Int) {
        val schedule = _schedules.value.find { schedule -> schedule.scheduleId == scheduleId }
        schedule?.let {
            viewModelScope.launch {
                scheduleRepository.deleteSchedule(it)
                recentlyDeletedSchedule = it
            }
        }
    }

    // 삭제 후 취소 시 복원
    fun restoreSchedule() {
        viewModelScope.launch{
            // null 이면 취소
            scheduleRepository.insertSchedule(recentlyDeletedSchedule ?: return@launch)
        }
    }


}