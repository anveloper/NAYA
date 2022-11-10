package com.youme.naya.database.repository

import com.youme.naya.database.dao.ScheduleDao
import com.youme.naya.database.entity.Member
import com.youme.naya.database.entity.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val dao : ScheduleDao
    ) : ScheduleRepository {

    override fun getMembers(): Flow<List<Member>> {
        return dao.getMembers()
    }

    override suspend fun getSchedulesByDate(scheduleDate: String): Flow<List<Schedule>> {
        return dao.getSchedulesByDate(scheduleDate).flowOn(Dispatchers.IO).conflate()
    }

    override suspend fun getScheduleById(id: Int): Schedule? {
        return dao.getScheduleById(id)
    }

    override suspend fun getMemberById(memberId: Int): Member? {
        return dao.getMemberById(memberId)
    }

//    override suspend fun getScheduleWithMembers(scheduleId: Int): List<ScheduleWithMembers> {
//        return dao.getScheduleWithMembers(scheduleId)
//    }

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