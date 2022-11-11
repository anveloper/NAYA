package com.youme.naya.screens


import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.youme.naya.widgets.home.CardListViewModel
import com.youme.naya.widgets.items.GalleryItem

@Composable
fun NayaCardScreen(
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val viewModel = viewModel<CardListViewModel>()
    viewModel.fetchCards()
    val cardList = viewModel.viewCards.value
    val listSize = cardList.size

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            viewModel.fetchCards()
        }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(cardList) { card ->
            GalleryItem(activity!!, card)
        }

    }


}

@Preview
@Composable
fun NayaCardScreenPreview() {
//    NayaCardScreen()
}
