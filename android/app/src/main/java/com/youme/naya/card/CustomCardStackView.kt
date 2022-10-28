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
fun CustomCardStackView() {
    val context = LocalContext.current
    val testData = listOf<String>(
        "Card 1",
        "Card 2",
        "Card 3",
        "Card 4",
        "Card 5",
        "Card 6",
        "Card 7",
        "Card 8",
        "Card 9",
    )

    AndroidViewBinding({ inflater, parent, _ ->
        CardStackViewMainBinding.inflate(inflater, parent)
    },
        Modifier
            .fillMaxSize()
            // Material Design 기준 Bottom Navigation 최소 높이는 56dp
            .padding(start = 16.dp, end = 16.dp, bottom = 56.dp)) {
        val mCardStackAdapter = CardStackAdapter(context);
        stackviewMain.setAdapter(mCardStackAdapter)
        mCardStackAdapter.updateData(testData)
    }
}