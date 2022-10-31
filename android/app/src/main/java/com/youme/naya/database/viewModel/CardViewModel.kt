package com.youme.naya.database.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.youme.naya.CardApplication
import com.youme.naya.database.CardDataSource
import com.youme.naya.database.entity.Card
import com.youme.naya.database.repository.CardRepository
import com.youme.naya.db.repository.NayaCardRepository
import com.youme.naya.db.viewModel.NayaCardViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(private val repository: CardRepository) : ViewModel() {

    private val _cardList = MutableStateFlow<List<Card>>(emptyList())
    val cardList = _cardList.asStateFlow()
//    private var cardList = mutableStateListOf<Card>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllCards().distinctUntilChanged().collect { listOfCards ->
                if (listOfCards.isNullOrEmpty()) {
                    Log.d("Empty", ": Empty list")
                } else {
                    _cardList.value = listOfCards
                }
            }
        }
//        cardList.addAll(CardDataSource().loadCards())
    }

    fun addCard(card: Card) = viewModelScope.launch {
        repository.addCard(card)
    }

    fun updateCard(card: Card) = viewModelScope.launch {
        repository.updateCard(card)
    }

    fun removeCard(card: Card) = viewModelScope.launch {
        repository.deleteCard(card)
    }

    fun removeAllCards() = viewModelScope.launch {
        repository.deleteAllCards()
    }

    fun getAllCards() = viewModelScope.launch {
        _cardList = repository.getAllCards()
    }
//    fun addCard(card: Card) {
//        cardList.add(card)
//    }
//    fun removeCard(card: Card) {
//        cardList.remove(card)
//    }
//    fun getAllCards(): List<Card> {
//        return cardList
//    }

}