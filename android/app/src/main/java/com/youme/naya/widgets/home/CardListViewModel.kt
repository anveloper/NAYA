package com.youme.naya.widgets.home

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class CardListViewModel(application: Application) : AndroidViewModel(application) {
    private val _cardUris = mutableStateOf(emptyList<Uri>())
    val cardUris: State<List<Uri>> = _cardUris

    fun fetchCards() {
        val uris = mutableListOf<Uri>()
        getApplication<Application>().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            "${MediaStore.Images.ImageColumns.DATE_TAKEN} DESC"
        )?.use { cursor ->
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                uris.add(contentUri)
            }
            _cardUris.value = uris
        }
    }
}