package com.youme.naya.database.repository

import com.youme.naya.database.entity.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getSchedules() : Flow<List<Schedule>>

    suspend fun getScheduleById(id: Int) : Schedule?

    suspend fun insertSchedule(schedule: Schedule)

    suspend fun updateSchedule(schedule: Schedule)

    suspend fun deleteSchedule(schedule: Schedule)

}