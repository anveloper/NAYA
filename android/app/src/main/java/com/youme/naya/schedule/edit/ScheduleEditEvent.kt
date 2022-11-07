package com.youme.naya.schedule.edit

import com.youme.naya.database.entity.Schedule

sealed class ScheduleEditEvent {
    data class DeleteSchedule(val schedule: Schedule): ScheduleEditEvent()
}
