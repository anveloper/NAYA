package com.youme.naya.database.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youme.naya.database.entity.Card
import com.youme.naya.database.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(private val repository: CardRepository) : ViewModel() {

    private val _cardList = MutableStateFlow<List<Card>>(emptyList())
    val cardList = _cardList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllCards().distinctUntilChanged().collect { listOfCards ->
                _cardList.value = listOfCards
            }
        }
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

}