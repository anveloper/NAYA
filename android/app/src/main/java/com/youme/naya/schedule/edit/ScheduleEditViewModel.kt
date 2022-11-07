package com.youme.naya.schedule.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youme.naya.schedule.useCases.DeleteSchedule
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleEditViewModel @Inject constructor(
    private val deleteSchedule: DeleteSchedule
): ViewModel() {

    fun onEvent(event: ScheduleEditEvent) {
        when (event) {
            is ScheduleEditEvent.DeleteSchedule -> {
                viewModelScope.launch {
                    deleteSchedule(event.schedule)
                }
            }
        }
    }
}