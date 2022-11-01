package com.youme.naya.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.youme.naya.database.dao.CardDao
import com.youme.naya.database.entity.Card

@Database(entities = [Card::class], version = 1, exportSchema = false)
abstract class CardDatabase: RoomDatabase() {
    abstract fun cardDao(): CardDao
}