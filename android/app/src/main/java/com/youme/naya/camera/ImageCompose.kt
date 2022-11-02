package com.youme.naya.camera

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ImageCompose(bitmap: Bitmap) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )

}


@Preview(
    name = "Capture Image",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CaptureImagePreview() {
    val bitmap = Bitmap.createBitmap(200, 360, Bitmap.Config.ARGB_8888)
    ImageCompose(bitmap)
}