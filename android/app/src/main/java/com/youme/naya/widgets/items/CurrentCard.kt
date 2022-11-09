package com.youme.naya.widgets.items

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.youme.naya.widgets.home.ViewCard

object CurrentCard {
    private val currentCard = mutableStateOf<ViewCard>(ViewCard(-1, Uri.EMPTY, ""))
    val getCurrentCard = currentCard

    fun setCurrentCard(viewCard: ViewCard) {
        currentCard.value = viewCard
        Log.i("CardListViewModel", viewCard.filename)
    }
}