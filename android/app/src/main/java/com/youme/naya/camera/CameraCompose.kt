package com.youme.naya.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Lens
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.youme.naya.utils.Commons.REQUIRED_PERMISSIONS

@Composable
fun CameraCompose(
    context: Context,
    cameraX: CameraX,
    onCaptureClick: () -> Unit,
) {
    var hasCamPermission by remember {
        mutableStateOf(
            REQUIRED_PERMISSIONS.all {
                ContextCompat.checkSelfPermission(context, it) ==
                        PackageManager.PERMISSION_GRANTED
            })
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { granted ->
            hasCamPermission =granted.size == 2
        }
    )
    LaunchedEffect(key1 = true) {
        launcher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }
    Column(modifier = Modifier.fillMaxSize()) {
        if (hasCamPermission) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { cameraX.startCameraPreviewView() }
            )
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(bottom = 60.dp),
        Arrangement.Bottom,
        Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onCaptureClick
        ) {
            Icon(
                imageVector = Icons.Sharp.Lens,
                contentDescription = "Take picture",
                tint = Color.White,
                modifier = Modifier
                    .size(100.dp)
                    .padding(1.dp)
                    .border(1.dp, Color.White, CircleShape)
            )
        }
    }
}