package com.youme.naya.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.youme.naya.camera.CameraCompose
import com.youme.naya.camera.CameraX
import com.youme.naya.utils.Commons

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