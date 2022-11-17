package com.youme.naya.card

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.widgets.items.CardItemPlus
import com.youme.naya.widgets.items.GalleryItem

@Composable
fun BusinessCardGridList(
    context: Context,
    navController: NavHostController,
    cardViewModel: CardViewModel = viewModel(),
    isNuya: Boolean = false
) {
    val cardList = if (isNuya) {
        cardViewModel.businessCardListInNuya.collectAsState().value
    } else {
        cardViewModel.businessCardListInNaya.collectAsState().value
    }

    if (cardList.isNotEmpty()) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cardList) { card ->
                GalleryItem(context as Activity, navController, bCard = card, enableShare = !isNuya)
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
            CardItemPlus(isBCard = true, isNuya = isNuya)
            Spacer(Modifier.height(16.dp))
            Text(text = "명함을 등록해 보세요", color = Color(0xFFCED3D6))
        }
    }
}