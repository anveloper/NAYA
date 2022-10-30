package com.youme.naya.db.repository

import androidx.annotation.WorkerThread
import com.youme.naya.db.dao.NayaCardDao
import com.youme.naya.db.entity.NayaCard
import kotlinx.coroutines.flow.Flow

class NayaCardRepository(private val nayaCardDao: NayaCardDao) {

    val allCards: Flow<List<NayaCard>> = nayaCardDao.getAllCards()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(nayaCard: NayaCard) {
        nayaCardDao.insert(nayaCard)
    }

}