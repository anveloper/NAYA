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
import com.youme.naya.widgets.home.CardListViewModel
import com.youme.naya.widgets.items.GalleryItem

@Composable
fun NayaCardGridList(context: Context) {
    val nayaCards = viewModel<CardListViewModel>()
    nayaCards.fetchCards()
    val cardList = nayaCards.viewCards.value

    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 16.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cardList) { card ->
            GalleryItem(context as Activity, nayaCard = card)
        }
    }
//        if (nayaCards.isEmpty()) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(bottom = 56.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Text("받은 나야 카드가 없어요", color = NeutralLight)
//            }
//        } else {
//            AndroidViewBinding(
//                { inflater, parent, _ ->
//                    CardStackViewMainBinding.inflate(inflater, parent)
//                },
//                Modifier
//                    .fillMaxSize()
//                    // Material Design 기준 Bottom Navigation 최소 높이는 56dp
//                    .padding(start = 16.dp, end = 16.dp, bottom = 56.dp)
//            ) {
//                val mCardStackAdapter = CardStackAdapter(context, launcher);
//                stackviewMain.setAdapter(mCardStackAdapter)
//                mCardStackAdapter.updateData(nayaCards)
//            }
//        }
}