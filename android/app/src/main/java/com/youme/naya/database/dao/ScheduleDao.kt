package com.youme.naya.database.dao

import androidx.room.*
import com.youme.naya.database.entity.Alarm
import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.entity.Member
import com.youme.naya.database.entity.relations.ScheduleWithMembers
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    /**
     * Schedule
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: Schedule)

    @Update
    suspend fun updateSchedule(schedule: Schedule)

    @Delete
    suspend fun deleteSchedule(schedule: Schedule)

    @Query("SELECT * FROM schedule")
    fun getSchedules(): Flow<List<Schedule>>

    // 스케줄 id로 조회
    @Query("SELECT * FROM schedule WHERE scheduleId = :scheduleId")
    suspend fun getScheduleById(scheduleId: Int): Schedule?

    @Query("SELECT * FROM schedule WHERE scheduleDate = :scheduleDate")
    fun getSchedulesByDate(scheduleDate: String): Flow<List<Schedule>>


    /**
     * Member
     */

    @Query("SELECT * FROM member")
    fun getMembers(): Flow<List<Member>>

    @Query("SELECT * FROM member WHERE scheduleId = :scheduleId")
    fun getMembersByScheduleId(scheduleId: Int): Flow<List<Member>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: Member)

    @Update
    suspend fun updateMember(member: Member)

    @Delete
    suspend fun deleteMember(member: Member)

    @Query("SELECT * FROM member WHERE memberId = :memberId")
    suspend fun getMemberById(memberId: Int): Member?



    /**
     * Schedule and Member
     */

    @Transaction
    @Query("SELECT * FROM schedule")
    fun getSchedulesWithMembers(): Flow<List<ScheduleWithMembers>>

    @Transaction
    @Query("SELECT * FROM schedule WHERE scheduleDate = :scheduleDate ORDER BY startTime DESC")
    fun getSchedulesWithMembersByDate(scheduleDate: String): Flow<List<ScheduleWithMembers>>

    @Transaction
    @Query("SELECT * FROM schedule WHERE scheduleId = :scheduleId")
    suspend fun getScheduleWithMembersById(scheduleId: Int): ScheduleWithMembers?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScheduleWithMembers(schedule: Schedule, members: List<Member>)

    @Transaction
    @Delete
    suspend fun deleteScheduleWithMembers(schedule: Schedule, members: List<Member>)


    /**
     * Alarm
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: Alarm)

    @Query("SELECT * FROM alarm")
    fun getAlarms(): Flow<List<Alarm>>

    @Query("SELECT * FROM alarm WHERE date = :date")
    fun getAlarmsByDate(date: String): Flow<List<Alarm>>

}