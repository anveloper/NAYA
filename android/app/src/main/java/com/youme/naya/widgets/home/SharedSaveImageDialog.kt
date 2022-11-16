package com.youme.naya.widgets.home

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.youme.naya.R
import com.youme.naya.components.OutlinedBigButton
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.ui.theme.fonts
import com.youme.naya.utils.saveSharedCardImage
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SharedSaveImageDialog(
    sharedImageUrl: String,
    setSaveImage: () -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val view = LocalView.current
    val captureController = rememberCaptureController()
    val (tmpImage, setTmpImage) = remember { mutableStateOf<Bitmap?>(null) }
    val imageBuilder = ImageRequest.Builder(LocalContext.current)
        .data(sharedImageUrl)
        .crossfade(true)
        .build()

    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(sharedImageUrl)
            .allowHardware(false) // Disable hardware bitmaps.
            .build()

        val result = (loader.execute(request) as SuccessResult).drawable
        val bitmap = (result as BitmapDrawable).bitmap
        setTmpImage(bitmap)
    }


    AlertDialog(onDismissRequest = { setSaveImage() }, buttons = {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            Arrangement.spacedBy(8.dp), Alignment.CenterHorizontally
        ) {
            Text(
                text = "공유된 이미지",
                fontFamily = fonts,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            AsyncImage(
                model = imageBuilder,
                placeholder = painterResource(R.drawable.sample_card),
                contentDescription = null,
                modifier = Modifier.size(250.dp, 450.dp)
            )

            PrimaryBigButton(text = "저장하기", tmpImage != null) {
                if (tmpImage != null) {
                    saveSharedCardImage(context, tmpImage, sharedImageUrl.contains("-BUSINESS-"))
                    setSaveImage()
                }
            }
            OutlinedBigButton(text = "취소") {
                setSaveImage()
            }
//            Text(text = sharedImageUrl)
        }
    })

}