package com.youme.naya.utils

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore


fun convertPath2Uri(context: Context, path: String): Uri {
    val cursor = context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        null,
        "_data = '$path'",
        null,
        null
    )

    cursor!!.moveToNext()
    val columnIdx = cursor.getColumnIndex("_id")
    val id = cursor.getInt(columnIdx)
    cursor.close()

    return ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toLong())
}