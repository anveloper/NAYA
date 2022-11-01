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

    @Query("SELECT * FROM naya_card")
    fun getAllCards(): Flow<List<Card>>

    @Query("SELECT * FROM naya_card where name=:name")
    suspend fun getCardByName(name: String): Card

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(card: Card)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(card: Card)

    @Query("DELETE FROM naya_card")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteCard(card: Card)

}