package com.youme.naya.widgets.items

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.MotionEvent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.youme.naya.share.ShareActivity
import com.youme.naya.widgets.home.ViewCard


private val GalleryModifier = Modifier
    .defaultMinSize(
        minWidth = 50.dp,
        minHeight = 90.dp
    )
    .shadow(elevation = 6.dp)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GalleryItem(
    activity: Activity,
    card: ViewCard
) {
    var (isShareOpen, setIsShareOpen) = remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { it ->
        Log.i("Activity Result", it.resultCode.toString())
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                setIsShareOpen(false)
            }
            Activity.RESULT_CANCELED -> {
                setIsShareOpen(false)
            }
        }
    }


    Card(
        GalleryModifier
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.i("Card", "Down ${card.uri}")
                    }
                    MotionEvent.ACTION_MOVE -> {
                        Log.i("Card", "Move ${card.uri}")
                    }
                    MotionEvent.ACTION_UP -> {
                        if (!isShareOpen) {
                            Log.i("Card", "Up ${card.uri}")
                            var intent = Intent(activity, ShareActivity::class.java)
                            intent.putExtra("cardUri", card.uri.toString())
                            intent.putExtra("filename", card.filename)
                            launcher.launch(intent)
                            setIsShareOpen(true)
                        }
                    }
                    else -> false
                }
                true
            }) {
        Image(rememberImagePainter(data = card.uri), null, Modifier.fillMaxSize())
    }
}
