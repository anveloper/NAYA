package com.youme.naya.schedule.useCases

import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.repository.ScheduleRepository
import javax.inject.Inject

class DeleteSchedule @Inject constructor(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(schedule: Schedule) {
        repository.deleteSchedule(schedule)
    }
}