package com.youme.naya.widgets.share

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.youme.naya.widgets.home.CardListViewModel
import com.youme.naya.widgets.items.GalleryItem

@Composable
fun ShareButtonDialog(
    activity: Activity,
    setDismiss: () -> Unit
) {
    val viewModel = viewModel<CardListViewModel>()
    viewModel.fetchCards()
    val cardList = viewModel.viewCards.value

    AlertDialog(
        onDismissRequest = { setDismiss() },
        buttons = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(cardList) { card ->
                        GalleryItem(activity, card)
                    }
                }
            }
        },
        backgroundColor = Color.Transparent,
        shape = RoundedCornerShape(20.dp)
    )
}