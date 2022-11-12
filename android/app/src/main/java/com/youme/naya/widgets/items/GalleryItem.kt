package com.youme.naya.widgets.items

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
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
import com.youme.naya.database.entity.Card
import com.youme.naya.share.ShareActivity
import com.youme.naya.utils.convertPath2Uri
import com.youme.naya.widgets.home.ViewCard
import java.io.File


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
    nayaCard: ViewCard? = null,
    bCard: Card? = null
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

    if (nayaCard != null && bCard == null) {
        Card(
            GalleryModifier
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            Log.i("Card", "Down ${nayaCard.uri}")
                        }
                        MotionEvent.ACTION_MOVE -> {
                            Log.i("Card", "Move ${nayaCard.uri}")
                        }
                        MotionEvent.ACTION_UP -> {
                            if (!isShareOpen) {
                                Log.i("Card", "Up ${nayaCard.uri}")
                                var intent = Intent(activity, ShareActivity::class.java)
                                intent.putExtra("cardUri", nayaCard.uri.toString())
                                intent.putExtra("filename", nayaCard.filename)
                                launcher.launch(intent)
                                setIsShareOpen(true)
                            }
                        }
                        else -> false
                    }
                    true
                }) {
            Image(rememberImagePainter(data = nayaCard.uri), null, Modifier.fillMaxSize())
        }
    } else if (nayaCard == null && bCard != null) {
        val bCardUri = convertPath2Uri(activity, bCard.path!!)
        Card(
            GalleryModifier
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            Log.i("Card", "Down ${bCardUri}")
                        }
                        MotionEvent.ACTION_MOVE -> {
                            Log.i("Card", "Move ${bCardUri}")
                        }
                        MotionEvent.ACTION_UP -> {
                            if (!isShareOpen) {
                                Log.i("Card", "Up ${bCardUri}")
                                var intent = Intent(activity, ShareActivity::class.java)
                                intent.putExtra("cardUri", bCardUri)
                                intent.putExtra(
                                    "filename",
                                    bCard.path.substring(bCard.path.lastIndexOf('/') + 1)
                                )
                                launcher.launch(intent)
                                setIsShareOpen(true)
                            }
                        }
                        else -> false
                    }
                    true
                }) {
            Image(
                rememberImagePainter(BitmapFactory.decodeFile(bCard.path)),
                null,
                Modifier.fillMaxSize()
            )
        }
    }
}
