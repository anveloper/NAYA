package com.youme.naya.widgets.items

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.MotionEvent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.youme.naya.share.ShareActivity
import com.youme.naya.widgets.home.CardListViewModel
import com.youme.naya.widgets.home.ViewCard


private val CardModifier = Modifier
    .width(200.dp)
    .height(360.dp)
    .shadow(elevation = 6.dp)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CardItem(card: ViewCard) {
    val context = LocalContext.current
    val activity = context as? Activity
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
        CardModifier
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    if (delta < 0 && !isShareOpen) {
                        var intent = Intent(activity, ShareActivity::class.java)
                        intent.putExtra("cardUri", card.uri.toString())
                        intent.putExtra("filename", card.filename)
                        launcher.launch(intent)
                        setIsShareOpen(true)
                    }
                }
            )
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.i("Card", "Down ${card.uri}")
                    }
                    MotionEvent.ACTION_MOVE -> {
                        Log.i("Card", "Move ${card.uri}")
                    }
                    MotionEvent.ACTION_UP -> {
                        Log.i("Card", "Up ${card.uri}")
                        CurrentCard.setCurrentCard(card)
                    }
                    else -> false
                }
                true
            }) {
        Image(rememberImagePainter(data = card.uri), null, Modifier.fillMaxSize())
    }
}
