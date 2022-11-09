package com.youme.naya.widgets.items

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.youme.naya.R
import com.youme.naya.custom.MediaCardActivity
import com.youme.naya.ocr.DocumentScannerActivity
import com.youme.naya.widgets.home.CardListViewModel

private val CardModifier = Modifier
    .width(200.dp)
    .height(360.dp)
    .shadow(elevation = 6.dp)

@Composable
fun CardItemPlus(isBCard: Boolean = false) {
    val context = LocalContext.current
    val activity = context as? Activity
    val viewModel = viewModel<CardListViewModel>()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        Log.i("Media Card Custom", it.resultCode.toString())
        if (it.resultCode == RESULT_OK) {
            viewModel.fetchCards()
        }
    }

    Card(CardModifier) {
        IconButton(onClick = {
            if (isBCard) {
                launcher.launch(Intent(activity, DocumentScannerActivity::class.java))
            } else {
                launcher.launch(Intent(activity, MediaCardActivity::class.java))
            }
        }) {
            Image(
                painter = painterResource(R.drawable.card_icon_plus),
                contentDescription = if (isBCard) "import business card" else "import naya card",
            )
        }
    }
}