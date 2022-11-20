package com.youme.naya.card

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.youme.naya.ui.theme.NeutralLight
import com.youme.naya.ui.theme.Typography
import com.youme.naya.widgets.home.CardListViewModel
import com.youme.naya.widgets.items.GalleryItemForSchedule

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NayaCardGridListForSchedule(context: Context, navController: NavHostController, isNuya: Boolean = false) {
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
                GalleryItemForSchedule(
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
            Text(text = "등록된 Nuya 카드가 없습니다.", color = NeutralLight, style = Typography.h6)
        }
    }
}