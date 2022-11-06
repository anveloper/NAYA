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

    private val _nayaCardList = MutableStateFlow<List<Card>>(emptyList())
    private val _businessCardList = MutableStateFlow<List<Card>>(emptyList())
    private val _selectResult = MutableStateFlow<Card?>(null)
    val nayaCardList = _nayaCardList.asStateFlow()
    val businessCardList = _businessCardList.asStateFlow()
    val selectResult = _selectResult.asStateFlow()

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

    fun getCardFromId(id: Int) = viewModelScope.launch {
        repository.getCardById(id).distinctUntilChanged().collect { card ->
            _selectResult.value = card
        }
    }

}