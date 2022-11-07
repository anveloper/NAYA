package com.youme.naya.database.repository

import com.youme.naya.database.dao.ScheduleDao
import com.youme.naya.database.entity.Member
import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.entity.relations.ScheduleWithMembers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val dao : ScheduleDao
    ) : ScheduleRepository {

    override fun getSchedules(): Flow<List<Schedule>> {
        return dao.getSchedules()
    }

    override fun getMembers(): Flow<List<Member>> {
        return dao.getMembers()
    }

    override suspend fun getScheduleById(id: Int): Schedule? {
        return dao.getScheduleById(id)
    }

    override suspend fun getScheduleByDate(scheduleDate: String): Schedule? {
        return dao.getScheduleByDate(scheduleDate)
    }

    override suspend fun getMemberById(memberId: Int): Member? {
        return dao.getMemberById(memberId)
    }

    override suspend fun getScheduleWithMembers(scheduleId: Int): List<ScheduleWithMembers> {
        return dao.getScheduleWithMembers(scheduleId)
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        return dao.insertSchedule(schedule)
    }

    override suspend fun insertMember(member: Member) {
        return dao.insertMember(member)
    }

    override suspend fun updateSchedule(schedule: Schedule) {
        return dao.updateSchedule(schedule)
    }

    override suspend fun updateMember(member: Member) {
        return dao.updateMember(member)
    }

    override suspend fun deleteSchedule(schedule: Schedule) {
        return dao.deleteSchedule(schedule)
    }

    override suspend fun deleteMember(member: Member) {
        return dao.deleteMember(member)
    }

}