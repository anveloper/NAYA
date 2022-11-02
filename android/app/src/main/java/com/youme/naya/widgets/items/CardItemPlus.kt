package com.youme.naya.widgets.items

import android.app.Activity
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
import com.youme.naya.R
import com.youme.naya.custom.MediaCardActivity

private val CardModifier = Modifier
    .width(200.dp)
    .height(360.dp)
    .shadow(elevation = 6.dp)

@Composable
fun CardItemPlus() {
    val context = LocalContext.current
    val activity = context as? Activity
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        Log.i("Media Card Custom", it.resultCode.toString())
    }

    Card(CardModifier) {
        IconButton(onClick = {
            launcher.launch(Intent(activity, MediaCardActivity::class.java))
        }) {
            Image(
                painter = painterResource(R.drawable.card_icon_plus),
                contentDescription = "import naya card",
            )

        }
    }
}