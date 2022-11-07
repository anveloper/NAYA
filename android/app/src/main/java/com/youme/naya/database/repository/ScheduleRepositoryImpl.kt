package com.youme.naya.database.repository

import com.youme.naya.database.dao.ScheduleDao
import com.youme.naya.database.entity.Schedule
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val dao : ScheduleDao
    ) : ScheduleRepository {

    override fun getSchedules(): Flow<List<Schedule>> {
        return dao.getSchedules()
    }

    override suspend fun getScheduleById(id: Int): Schedule? {
        return dao.getScheduleById(id)
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        return dao.insert(schedule)
    }

    override suspend fun updateSchedule(schedule: Schedule) {
        return dao.update(schedule)
    }

    override suspend fun deleteSchedule(schedule: Schedule) {
        return dao.delete(schedule)
    }

}