package com.youme.naya.utils

import android.graphics.Bitmap
import android.graphics.Matrix

fun rotateBitmap(bitmap: Bitmap, degree: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degree)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}