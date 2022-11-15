package com.youme.naya.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.youme.naya.database.dao.CardDao
import com.youme.naya.database.dao.ScheduleDao
import com.youme.naya.database.entity.Card
import com.youme.naya.database.entity.Member
import com.youme.naya.database.entity.Schedule

@Database(
    entities = [Card::class, Schedule::class, Member::class],
    version = 7,
    exportSchema = false
)
abstract class CardDatabase: RoomDatabase() {
    abstract fun cardDao(): CardDao
    abstract fun scheduleDao() : ScheduleDao
}