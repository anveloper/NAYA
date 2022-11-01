package com.youme.naya.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.youme.naya.db.dao.NayaCardDao
import com.youme.naya.db.entity.NayaCard

@Database(entities = arrayOf(NayaCard::class), version = 1, exportSchema = false)
public abstract class NayaDatabase: RoomDatabase() {

    abstract fun nayaCardDao(): NayaCardDao

    companion object {
        @Volatile
        private var INSTANCE: NayaDatabase? = null

        fun getDatabase(context: Context): NayaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NayaDatabase::class.java,
                    "naya_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}