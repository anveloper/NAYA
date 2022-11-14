package com.youme.naya.custom

import android.graphics.Bitmap
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner

@Composable
fun CameraWrapper(
    cameraSelector: Int = CameraSelector.LENS_FACING_FRONT,
    setTmpBitmap: (Bitmap) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraX = CustomCameraX(context, lifecycleOwner, cameraSelector)
    Box(Modifier.fillMaxSize()) {
        CustomCamera(context, cameraX) {
            cameraX.capturePhoto() { bitmap ->
                setTmpBitmap(bitmap)
            }
        }
    }
}