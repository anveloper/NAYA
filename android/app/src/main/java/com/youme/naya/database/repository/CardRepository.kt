package com.youme.naya.database.repository

import com.youme.naya.database.dao.CardDao
import com.youme.naya.database.entity.Card
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CardRepository @Inject constructor(private val cardDao: CardDao) {

    suspend fun addCard(card: Card) = cardDao.insert(card)

    suspend fun updateCard(card: Card) = cardDao.update(card)

    suspend fun deleteCard(card: Card) = cardDao.deleteCard(card)

    suspend fun deleteAllCards() = cardDao.deleteAll()

    fun getNayaCards(): Flow<List<Card>> = cardDao.getNayaCards().flowOn(Dispatchers.IO).conflate()

    fun getBusinessCards(): Flow<List<Card>> = cardDao.getBusinessCards().flowOn(Dispatchers.IO).conflate()

}