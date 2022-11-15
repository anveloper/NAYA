package com.youme.naya.card

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.youme.naya.widgets.home.CardListViewModel
import com.youme.naya.widgets.items.GalleryItem

@Composable
fun NayaCardGridList(context: Context, navController: NavHostController, isNuya: Boolean = false) {
    val nayaCards = viewModel<CardListViewModel>()
    if (isNuya) {
        nayaCards.fetchNuyaCards()
    } else {
        nayaCards.fetchNayaCards()
    }
    val cardList = nayaCards.viewCards.value

    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 16.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cardList) { card ->
            GalleryItem(context as Activity, navController, nayaCard = card)
        }
    }
}