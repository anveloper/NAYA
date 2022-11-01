package com.youme.naya.screens

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.youme.naya.camera.CameraCompose
import com.youme.naya.camera.CameraX
import com.youme.naya.camera.ImageCompose
import com.youme.naya.utils.Commons

@Composable
fun CameraScreen() {
    // compose lifecycle을 위한 겉 레이아웃 역할
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var tmpBitmap by rememberSaveable {
        mutableStateOf<Bitmap?>(null)
    }
    val cameraX = CameraX(context, lifecycleOwner)

    if (tmpBitmap == null) {
        CameraCompose(context, cameraX) {
            if (Commons.allPermissionsGranted(context)) {
                cameraX.capturePhoto() { bitmap ->
                    tmpBitmap = bitmap
                }
            }
        }

    } else {
        ImageCompose(tmpBitmap!!)
    }
}