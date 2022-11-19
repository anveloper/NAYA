package com.youme.naya.database.repository

import androidx.lifecycle.asLiveData
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

    fun getBusinessCardsInNaya(): Flow<List<Card>> = cardDao.getBusinessCardsInNaya().flowOn(Dispatchers.IO).conflate()

    fun getBusinessCardsInNuya(): Flow<List<Card>> = cardDao.getBusinessCardsInNuya().flowOn(Dispatchers.IO).conflate()

    fun getCardById(id: Int): Flow<Card> = cardDao.getCardById(id).flowOn(Dispatchers.IO).conflate()

}