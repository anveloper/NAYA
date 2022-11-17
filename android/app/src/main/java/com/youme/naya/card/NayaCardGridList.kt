package com.youme.naya.card

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.youme.naya.widgets.home.CardListViewModel
import com.youme.naya.widgets.items.CardItemPlus
import com.youme.naya.widgets.items.GalleryItem

@Composable
fun NayaCardGridList(context: Context, navController: NavHostController, isNuya: Boolean = false) {
    val nayaCards = viewModel<CardListViewModel>()
    if (isNuya) {
        nayaCards.fetchNayaCardsInNuya()
    } else {
        nayaCards.fetchNayaCards()
    }
    val cardList = nayaCards.viewCards.value

    if (cardList.isNotEmpty()) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 72.dp),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cardList) { card ->
                GalleryItem(
                    context as Activity,
                    navController,
                    nayaCard = card,
                    enableShare = !isNuya
                )
            }
        }
    } else {
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardItemPlus(isNuya = isNuya)
            Spacer(Modifier.height(16.dp))
            Text(text = "카드를 등록해 보세요", color = Color(0xFFCED3D6))
        }
    }
}