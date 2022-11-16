package com.youme.naya.card

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.widgets.home.CardListViewModel
import com.youme.naya.widgets.items.GalleryItem

@Composable
fun BusinessCardGridList(
    context: Context,
    navController: NavHostController,
    cardViewModel: CardViewModel = viewModel(),
    isNuya: Boolean = false
) {
    val businessCardsInNaya = cardViewModel.businessCardList.collectAsState().value
    val cardListViewModel = viewModel<CardListViewModel>()
    cardListViewModel.fetchBusinessCardsInNuya()
    val businessCardsInNuya = cardListViewModel.viewCards.value

    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isNuya) {
            items(businessCardsInNuya) { card ->
                GalleryItem(
                    context as Activity,
                    navController,
                    nayaCard = card,
                    enableShare = !isNuya
                )
            }
        } else {
            items(businessCardsInNaya) { card ->
                GalleryItem(context as Activity, navController, bCard = card, enableShare = !isNuya)
            }
        }
    }
}