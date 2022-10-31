package com.youme.naya.db.viewModel

import androidx.lifecycle.*
import com.youme.naya.db.entity.NayaCard
import com.youme.naya.db.repository.NayaCardRepository
import kotlinx.coroutines.launch

class NayaCardViewModel(private val repository: NayaCardRepository) : ViewModel() {

    val allCards: LiveData<List<NayaCard>> = repository.allCards.asLiveData()

    fun insert(nayaCard: NayaCard) = viewModelScope.launch {
        repository.insert(nayaCard)
    }

}

class NayaCardViewModelFactory(private val repository: NayaCardRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NayaCardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NayaCardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}