package com.youme.naya.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.youme.naya.camera.CameraCompose
import com.youme.naya.camera.CameraX
import com.youme.naya.camera.ImageCompose
import com.youme.naya.utils.Commons
import java.io.File
import java.util.concurrent.ExecutorService
import android.net.Uri as Uri

@SuppressLint("UnrememberedMutableState")
@Composable
fun CameraScreen() {
    // compose lifecycle을 위한 겉 레이아웃 역할
    var context = LocalContext.current
    var lifecycleOwner = LocalLifecycleOwner.current
    var cameraX = CameraX(context, lifecycleOwner)
    CameraCompose(context, cameraX) {
        if (Commons.allPermissionsGranted(context)) {
            cameraX.capturePhoto()
        }
    }
}