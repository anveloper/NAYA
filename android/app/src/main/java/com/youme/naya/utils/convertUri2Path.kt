package com.youme.naya.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.loader.content.CursorLoader

fun convertUri2Path(context: Context, uri: Uri): String {
    val proj = arrayOf(MediaStore.Images.Media.DATA)
    val cursorLoader = CursorLoader(context, uri, proj, null, null, null)
    val cursor: Cursor = cursorLoader.loadInBackground()!!
    val index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor.moveToFirst()

    val result = cursor.getString(index)
    cursor.close()
    return result
}