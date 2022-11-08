package com.youme.naya.database.repository

import androidx.room.*
import com.youme.naya.database.entity.Member
import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.entity.relations.ScheduleWithMembers
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {

    fun getSchedules() : Flow<List<Schedule>>
    fun getMembers(): Flow<List<Member>>

    suspend fun getScheduleById(id: Int) : Schedule?
    suspend fun getScheduleByDate(scheduleDate: String): Schedule?
    suspend fun getMemberById(memberId: Int): Member?

//    suspend fun getScheduleWithMembers(scheduleId: Int): List<ScheduleWithMembers>

    suspend fun insertSchedule(schedule: Schedule)
    suspend fun insertMember(member: Member)

    suspend fun updateSchedule(schedule: Schedule)
    suspend fun updateMember(member: Member)

    suspend fun deleteSchedule(schedule: Schedule)
    suspend fun deleteMember(member: Member)

}