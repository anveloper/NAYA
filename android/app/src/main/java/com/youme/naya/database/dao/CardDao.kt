package com.youme.naya.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.youme.naya.database.entity.Card
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Query("SELECT * FROM naya_card WHERE kind = 0")
    fun getNayaCards(): Flow<List<Card>>

    @Query("SELECT * FROM naya_card WHERE kind = 1")
    fun getBusinessCards(): Flow<List<Card>>

    @Query("SELECT * FROM naya_card WHERE NayaCardId = :id")
    fun getCardById(id: Int): Flow<Card>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(card: Card)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(card: Card)

    @Query("DELETE FROM naya_card")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteCard(card: Card)

}