package com.youme.naya.database.viewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.youme.naya.database.entity.Card
import com.youme.naya.database.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(private val repository: CardRepository) : ViewModel() {

    private val _businessCardListInNaya = MutableStateFlow<List<Card>>(emptyList())
    private val _businessCardListInNuya = MutableStateFlow<List<Card>>(emptyList())
    private val _selectResult = MutableStateFlow<Card?>(null)
    val businessCardListInNaya = _businessCardListInNaya.asStateFlow()
    val businessCardListInNuya = _businessCardListInNuya.asStateFlow()
    val selectResult = _selectResult.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBusinessCardsInNaya().distinctUntilChanged().collect { listOfCards ->
                _businessCardListInNaya.value = listOfCards
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBusinessCardsInNuya().distinctUntilChanged().collect { listOfCards ->
                _businessCardListInNuya.value = listOfCards
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

    fun getCardFromId(id: Int) = viewModelScope.launch {
        repository.getCardById(id).distinctUntilChanged().collect { card ->
            _selectResult.value = card
        }
    }

}