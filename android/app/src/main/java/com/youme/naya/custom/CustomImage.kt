package com.youme.naya.custom

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun CustomImage(bitmap: Bitmap) {
    Image(bitmap.asImageBitmap(), null)
}