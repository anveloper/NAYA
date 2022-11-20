package com.youme.naya.widgets.items

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.youme.naya.card.CardImagePlaceholder
import com.youme.naya.database.entity.Card
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.utils.convertPath2Uri
import com.youme.naya.utils.rotateBitmap
import com.youme.naya.widgets.home.ViewCard
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


private val GalleryModifier = Modifier
    .fillMaxWidth()
    .aspectRatio(5 / 9f)
    .shadow(elevation = 6.dp)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GalleryItemForSchedule(
    activity: Activity,
    navController: NavHostController,
    nayaCard: ViewCard? = null,
    enableShare: Boolean = true,
    bCard: Card? = null,
    viewModel: ScheduleMainViewModel = hiltViewModel(),
) {
    val bCardBitmap =
        if (bCard?.path != null && File(bCard.path).exists()) BitmapFactory.decodeFile(bCard.path) else null

    if (nayaCard != null && bCard == null) {
        Card(
            GalleryModifier
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_UP -> {
                            viewModel.onUriChange(nayaCard.uri.toString())
                            viewModel.onNuyaTypeChange(0)
                        }
                        else -> false
                    }
                    true
                },
            shape = RectangleShape
        ) {
            ImageContainer(nayaCard.uri)
        }

    } else if (nayaCard == null && bCard != null) {
//        val bCardUri = convertPath2Uri(activity, bCard.path!!)
        Card(
            GalleryModifier
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_UP -> {
                            if (bCardBitmap != null) {
                                BitmapToString(bCardBitmap)?.let { it1 ->
                                    viewModel.onUriChange(
                                        it1)
                                }
                                viewModel.onNuyaTypeChange(1)
                            }
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
    }
}


/*
     * Bitmap을 String형으로 변환
     * */
fun BitmapToString(bitmap: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos)
    val bytes: ByteArray = baos.toByteArray()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}
