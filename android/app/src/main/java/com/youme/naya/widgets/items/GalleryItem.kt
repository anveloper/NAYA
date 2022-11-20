package com.youme.naya.widgets.items

import android.app.Activity
import android.graphics.BitmapFactory
import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.youme.naya.card.CardDetailsDialog
import com.youme.naya.card.CardImagePlaceholder
import com.youme.naya.database.entity.Card
import com.youme.naya.utils.rotateBitmap
import com.youme.naya.widgets.home.ViewCard
import java.io.File


private val GalleryModifier = Modifier
    .fillMaxWidth()
    .aspectRatio(5 / 9f)
    .shadow(elevation = 6.dp)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GalleryItem(
    activity: Activity,
    navController: NavHostController,
    nayaCard: ViewCard? = null,
    enableShare: Boolean = true,
    bCard: Card? = null
) {
    var isDetailsDialogOpened by remember { mutableStateOf(false) }
    val bCardBitmap =
        if (bCard?.path != null && File(bCard.path).exists()) BitmapFactory.decodeFile(bCard.path) else null

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
                            isDetailsDialogOpened = true
                        }
                        else -> false
                    }
                    true
                },
            shape = RectangleShape
        ) {
            ImageContainer(nayaCard.uri)
        }
        if (isDetailsDialogOpened) {
            CardDetailsDialog(
                activity,
                navController,
                nayaCard = nayaCard,
                enableShare = enableShare,
            ) {
                isDetailsDialogOpened = false
            }
        }
    } else if (nayaCard == null && bCard != null) {
        Card(
            GalleryModifier
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                        }
                        MotionEvent.ACTION_MOVE -> {
                        }
                        MotionEvent.ACTION_UP -> {
                            isDetailsDialogOpened = true
                        }
                        else -> false
                    }
                    true
                },
            shape = RectangleShape
        ) {
            if (bCardBitmap != null) {
                ImageContainer(rotateBitmap(bCardBitmap, 90f))
            } else {
                CardImagePlaceholder()
            }
        }
        if (isDetailsDialogOpened) {
            CardDetailsDialog(
                activity,
                navController,
                bCard = bCard,
                enableShare = enableShare
            ) {
                isDetailsDialogOpened = false
            }
        }
    }
}

@Composable
fun ImageContainer(data: Any) {
    Image(
        painter = rememberAsyncImagePainter(data),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}