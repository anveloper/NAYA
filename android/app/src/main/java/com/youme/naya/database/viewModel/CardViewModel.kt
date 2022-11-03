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

    private val _nayaCardList = MutableStateFlow<List<Card>>(emptyList())
    private val _businessCardList = MutableStateFlow<List<Card>>(emptyList())
    val nayaCardList = _nayaCardList.asStateFlow()
    val businessCardList = _businessCardList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNayaCards().distinctUntilChanged().collect { listOfCards ->
                _nayaCardList.value = listOfCards
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBusinessCards().distinctUntilChanged().collect { listOfCards ->
                _businessCardList.value = listOfCards
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