package com.youme.naya.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.databinding.CardStackViewMainBinding
import com.youme.naya.ui.theme.NeutralLight

@Composable
fun CustomCardStackView(cardViewModel: CardViewModel = viewModel(), isBCard: Boolean = false) {
    val nayaCards = cardViewModel.nayaCardList.collectAsState().value
    val businessCards = cardViewModel.businessCardList.collectAsState().value
    val context = LocalContext.current

    if (isBCard) {
        if (businessCards.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 56.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("보유 중인 명함이 없어요", color = NeutralLight)
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
                val mCardStackAdapter = CardStackAdapter(context);
                stackviewMain.setAdapter(mCardStackAdapter)
                mCardStackAdapter.updateData(businessCards)
            }
        }
    } else {
        if (nayaCards.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 56.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("보유 중인 나야 카드가 없어요", color = NeutralLight)
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
                val mCardStackAdapter = CardStackAdapter(context);
                stackviewMain.setAdapter(mCardStackAdapter)
                mCardStackAdapter.updateData(nayaCards)
            }
        }
    }
}