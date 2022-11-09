package com.youme.naya.screens


import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.youme.naya.ocr.DocumentScannerActivity
import com.youme.naya.share.NfcActivity
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
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel.fetchCards()
            }
        }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        // 이 Row 삭제 하면 됩니다
        item() {
            Column(
                modifier = Modifier
                    .fillMaxSize(), Arrangement.SpaceEvenly, Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    context.startActivity(Intent(context, NfcActivity::class.java))
                }) {
                    Text(text = "nfc", fontSize = 16.sp)
                }
                Button(onClick = {
                    launcher.launch(Intent(activity, DocumentScannerActivity::class.java))
                }) {
                    Text(text = "ocr", fontSize = 16.sp)
                }
            }
        }
        // 여기까지


        items(cardList) { card ->
            GalleryItem(card)
        }

    }


}

@Preview
@Composable
fun NayaCardScreenPreview() {
//    NayaCardScreen()
}
