package com.youme.naya.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.youme.naya.db.entity.NayaCard
import kotlinx.coroutines.flow.Flow

@Dao
interface NayaCardDao {
    @Query("SELECT * FROM naya_card")
    fun getAllCards(): Flow<List<NayaCard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(card: NayaCard)

    @Query("DELETE FROM naya_card")
    suspend fun deleteAll()
}