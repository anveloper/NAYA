package com.youme.naya.card

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.databinding.CardStackViewMainBinding
import com.youme.naya.ui.theme.NeutralLight

@Composable
fun CustomCardListView(
    context: Context,
    cardViewModel: CardViewModel = viewModel(),
) {
    val businessCards = cardViewModel.businessCardList.collectAsState().value

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        when (it.resultCode) {
            Activity.RESULT_OK -> {
            }
            Activity.RESULT_CANCELED -> {
            }
        }
    }

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
}