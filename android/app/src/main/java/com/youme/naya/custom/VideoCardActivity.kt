package com.youme.naya.custom

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.youme.naya.BaseActivity
import com.youme.naya.ui.theme.AndroidTheme

class VideoCardActivity : BaseActivity(TransitionMode.HORIZON) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = LocalContext.current as? Activity
            AndroidTheme() {
                VideoCardScreen(
                    { bitmap ->
//                        saveCardImage(baseContext, bitmap)
                        intent.putExtra("Custom Exit", 1)
                        setResult(RESULT_OK, intent)
                        activity?.finish()
                    },
                    {
                        intent.putExtra("finish", 0)
                        setResult(RESULT_OK, intent)
                        activity?.finish()
                    }
                )
            }
        }
    }
}

@Composable
fun VideoCardScreen(
    createNayaCardFile: (Bitmap) -> Unit,
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val density = LocalDensity.current
    val view = LocalView.current

    val tmpBitmapList = remember {
        mutableListOf<Bitmap>()
    }

    val cameraX = CustomCameraX(context, lifecycleOwner)
    Box(Modifier.fillMaxSize()) {
        if (tmpBitmapList.size < 8) {
            CustomCamera(context, cameraX) {
                for (t in 1..8) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        cameraX.capturePhoto() { bitmap ->
                            tmpBitmapList.add(bitmap)
                        }
                    }, 1000 / 8)
                }
            }
        } else {
            // tmp
            LazyVerticalGrid(
                columns = GridCells.Fixed(6),
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(tmpBitmapList) { sticker ->
                    IconButton(onClick = {
                    }) {
                        Image(sticker.asImageBitmap(), null)
                    }
                }
            }
        }
    }
}