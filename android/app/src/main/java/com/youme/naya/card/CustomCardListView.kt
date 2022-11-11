package com.youme.naya.card

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.databinding.CardStackViewMainBinding
import com.youme.naya.ocr.DocumentScannerActivity
import com.youme.naya.share.NfcActivity
import com.youme.naya.ui.theme.NeutralLight
import com.youme.naya.widgets.home.CardListViewModel
import com.youme.naya.widgets.items.GalleryItem

@Composable
fun CustomCardListView(
    cardViewModel: CardViewModel = viewModel(),
    isBCard: Boolean = false
) {
//    val nayaCards = cardViewModel.nayaCardList.collectAsState().value
    val nayaCards = viewModel<CardListViewModel>()
    nayaCards.fetchCards()
    val cardList = nayaCards.viewCards.value

    val businessCards = cardViewModel.businessCardList.collectAsState().value
    val context = LocalContext.current
    var isCardTypeBCard by remember { mutableStateOf(isBCard) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                isCardTypeBCard = true
            }
            Activity.RESULT_CANCELED -> {
            }
        }
    }

    if (isCardTypeBCard) {
        if (businessCards.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 56.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("받은 명함이 없어요", color = NeutralLight)
            }
        } else {
            AndroidViewBinding(
                { inflater, parent, _ ->
                    CardStackViewMainBinding.inflate(inflater, parent)
                },
                Modifier
                    .fillMaxSize()
                    // Material Design 기준 Bottom Navigation 최소 높이는 56dp
                    .padding(start = 16.dp, end = 16.dp, bottom = 56.dp)
            ) {
                val mCardStackAdapter = CardStackAdapter(context, launcher);
                stackviewMain.setAdapter(mCardStackAdapter)
                mCardStackAdapter.updateData(businessCards)
            }
        }
    } else {
        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 16.dp),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cardList) { card ->
                GalleryItem(card)
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
}