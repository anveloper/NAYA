package com.youme.naya.database.dao

import androidx.room.*
import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.entity.Member
import com.youme.naya.database.entity.relations.ScheduleWithMembers
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM schedule ORDER BY date DESC")
    fun getSchedules(): Flow<List<Schedule>>

    @Query("SELECT * FROM member")
    fun getMembers(): Flow<List<Member>>

    @Query("SELECT * FROM schedule WHERE scheduleId = :scheduleId")
    suspend fun getScheduleById(scheduleId: Int): Schedule?

    @Query("SELECT * FROM member WHERE memberId = :memberId")
    suspend fun getMemberById(memberId: Int): Member?

    @Transaction
    @Query("SELECT * FROM schedule WHERE scheduleId = :scheduleId")
    suspend fun getScheduleWithMembers(scheduleId: Int): List<ScheduleWithMembers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: Schedule)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: Member)

    @Update
    suspend fun update(schedule: Schedule)

    @Update
    suspend fun updateMember(member: Member)

    @Delete
    suspend fun delete(schedule: Schedule)

    @Delete
    suspend fun deleteMember(member: Member)

}