package com.youme.naya.widgets.items

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.youme.naya.card.CardFace
import com.youme.naya.card.CardImagePlaceholder
import com.youme.naya.card.FlippableCard
import com.youme.naya.card.RotationAxis
import com.youme.naya.database.entity.Card
import com.youme.naya.share.ShareActivity
import com.youme.naya.utils.convertPath2Uri
import com.youme.naya.utils.rotateBitmap
import com.youme.naya.widgets.home.ViewCard
import java.io.File


private val CardModifier = Modifier
    .width(200.dp)
    .height(360.dp)
    .shadow(elevation = 6.dp)

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun CardItem(nayaCard: ViewCard? = null, bCard: Card? = null, flipState: CardFace) {
    val context = LocalContext.current
    val activity = context as? Activity

    var isShareOpened by remember { mutableStateOf(false) }
    val bCardBitmap =
        if (bCard?.path != null && File(bCard.path).exists()) BitmapFactory.decodeFile(bCard.path) else null
    val bCardBackgroundBitmap =
        if (bCard?.background != null && File(bCard.background).exists()) BitmapFactory.decodeFile(bCard.background) else null

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        Log.i("Activity Result", it.resultCode.toString())
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                isShareOpened = false
            }
            Activity.RESULT_CANCELED -> {
                isShareOpened = false
            }
        }
    }

    FlippableCard(
        cardFace = flipState,
        onClick = {},
        axis = RotationAxis.AxisY,
        modifier = CardModifier
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    if (delta < 0 && !isShareOpened) {
                        if (nayaCard != null && bCard == null) {
                            val intent = Intent(activity, ShareActivity::class.java)
                            intent.putExtra("cardUri", nayaCard.uri.toString())
                            intent.putExtra("filename", nayaCard.filename)
                            launcher.launch(intent)
                        } else if (nayaCard == null && bCard != null) {
                            val bCardUri = convertPath2Uri(context, bCard.path!!).toString()
                            Log.i("Card", "Up $bCardUri")
                            val intent = Intent(activity, ShareActivity::class.java)
                            intent.putExtra("cardUri", bCardUri)
                            intent.putExtra(
                                "filename",
                                bCard.path.substring(bCard.path.lastIndexOf('/') + 1)
                            )
                            launcher.launch(intent)
                        }
                        isShareOpened = true
                    }
                }
            )
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
//                        Log.i("Card", "Down ${nayaCard.uri}")
                    }
                    MotionEvent.ACTION_MOVE -> {
//                        Log.i("Card", "Move ${nayaCard.uri}")
                    }
                    MotionEvent.ACTION_UP -> {
//                        Log.i("Card", "Up ${nayaCard.uri}")
                        if (nayaCard != null && bCard == null) {
                            CurrentCard.setCurrentCard(nayaCard)
                        }
                    }
                    else -> false
                }
                true
            },
        back = if (nayaCard == null && bCard != null && bCard.background != null) ({
            if (bCardBackgroundBitmap != null) {
                Image(
                    painter = rememberAsyncImagePainter(
                        rotateBitmap(bCardBackgroundBitmap, 90f)
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                CardImagePlaceholder()
            }
        }) else null) {
        if (nayaCard != null && bCard == null) {
            Image(rememberAsyncImagePainter(nayaCard.uri), null, Modifier.fillMaxSize())
        } else if (nayaCard == null && bCard != null) {
            if (bCardBitmap != null) {
                Image(
                    painter = rememberAsyncImagePainter(
                        rotateBitmap(bCardBitmap, 90f)
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                CardImagePlaceholder()
            }
        }
    }
}
