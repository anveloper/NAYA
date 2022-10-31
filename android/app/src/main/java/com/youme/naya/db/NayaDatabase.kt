package com.youme.naya.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.youme.naya.db.dao.NayaCardDao
import com.youme.naya.db.entity.NayaCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [NayaCard::class], version = 1, exportSchema = false)
public abstract class NayaDatabase : RoomDatabase() {

    abstract fun nayaCardDao(): NayaCardDao

    companion object {
        @Volatile
        private var INSTANCE: NayaDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NayaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NayaDatabase::class.java,
                    "naya_database"
                )
                    .addCallback(NayaDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class NayaDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.nayaCardDao())
                    }
                }
            }

            suspend fun populateDatabase(nayaCardDao: NayaCardDao) {
                nayaCardDao.deleteAll()

                var card = NayaCard(1, "김성찬", "010-0000-0000")
                nayaCardDao.insert(card)
                card = NayaCard(2, "김성찬2", "010-0000-0001")
                nayaCardDao.insert(card)
            }

        }
    }

}