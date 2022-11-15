package com.youme.naya.database.repository

import com.youme.naya.database.entity.Member
import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.entity.relations.ScheduleWithMembers
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {

    /**
     * Schedule
     */

    fun getSchedules(): Flow<List<Schedule>>

    suspend fun insertSchedule(schedule: Schedule)

    suspend fun updateSchedule(schedule: Schedule)

    suspend fun deleteSchedule(schedule: Schedule)

    suspend fun getScheduleById(scheduleId: Int): Schedule?

    suspend fun getSchedulesByDate(scheduleDate: String): Flow<List<Schedule>>


    /**
     * Member
     */

    fun getMembers(): Flow<List<Member>>

    suspend fun insertMember(member: Member)

    suspend fun updateMember(member: Member)

    suspend fun deleteMember(member: Member)

    suspend fun getMemberById(memberId: Int): Member?

    fun getMembersByScheduleId(scheduleId: Int): Flow<List<Member>>

    /**
     * Schedule and Member
     */

    fun getSchedulesWithMembers(): Flow<List<ScheduleWithMembers>>

    fun getSchedulesWithMembersByDate(scheduleDate: String): Flow<List<ScheduleWithMembers>>

    suspend fun getScheduleWithMembersById(scheduleId: Int): ScheduleWithMembers?

    suspend fun insertScheduleWithMembers(schedule: Schedule, members: List<Member>)

    suspend fun deleteScheduleWithMembers(schedule: Schedule, members: List<Member>)

}