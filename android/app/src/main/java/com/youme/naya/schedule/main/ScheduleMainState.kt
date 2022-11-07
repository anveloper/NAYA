package com.youme.naya.schedule.main

import com.youme.naya.database.entity.Schedule

data class ScheduleMainState (
    val schedules: List<Schedule> = emptyList()
)
