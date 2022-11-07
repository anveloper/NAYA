package com.youme.naya.card

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.youme.naya.databinding.CardStackViewMainBinding

@Composable
fun CustomCardStackView(cards: List<String>) {
    val context = LocalContext.current

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
        mCardStackAdapter.updateData(cards)
    }
}