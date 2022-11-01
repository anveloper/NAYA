package com.youme.naya.model.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.*
import com.youme.naya.db.entity.NayaCard
import com.youme.naya.db.repository.NayaCardRepository
import com.youme.naya.model.CardDataSource
import com.youme.naya.model.entity.Card
import kotlinx.coroutines.launch

class CardViewModel: ViewModel() {

    private var cardList = mutableStateListOf<Card>()

    init {
        cardList.addAll(CardDataSource().loadCards())
    }

    fun addCard(card: Card) {
        cardList.add(card)
    }
    fun removeCard(card: Card) {
        cardList.remove(card)
    }
    fun getAllCards(): List<Card> {
        return cardList
    }

}