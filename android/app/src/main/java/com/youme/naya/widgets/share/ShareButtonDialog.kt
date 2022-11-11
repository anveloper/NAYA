package com.youme.naya.widgets.share

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.youme.naya.ui.theme.NeutralWhite
import com.youme.naya.ui.theme.PrimaryDark
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
                    .height(380.dp)
                    .background(NeutralWhite)
                    .padding(vertical = 16.dp),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "공유할 사진을 선택하세요",
                    color = PrimaryDark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(16.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    contentPadding = PaddingValues(8.dp),
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
