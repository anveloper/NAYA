package com.youme.naya.firebase

import android.net.Uri
import androidx.compose.runtime.mutableStateOf

class ImageUrlModel {
    private val _imageUrl = mutableStateOf<Uri?>(null)
    val imageUrl = _imageUrl

    init {

    }


}