package com.youme.naya.schedule.useCases

import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSchedules @Inject constructor(
    private val repository: ScheduleRepository
) {
    operator fun invoke(): Flow<List<Schedule>> {
        return repository.getSchedules()
    }
}