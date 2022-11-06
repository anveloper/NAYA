package com.youme.naya.widgets.home

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class ViewCard(uri: Uri, filename: String) {
    val uri = uri
    val filename = filename
}

class CardListViewModel(application: Application) : AndroidViewModel(application) {
    private val _cardUris = mutableStateOf(emptyList<ViewCard>())
    val cardUris: State<List<ViewCard>> = _cardUris

    fun fetchCards() {
        val viewCards = mutableListOf<ViewCard>()
        getApplication<Application>().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Files.FileColumns.TITLE + " LIKE ?",
            arrayOf("NAYA-MEDIA-%"),
            "${MediaStore.Images.ImageColumns.DATE_TAKEN} ASC"
        )?.use { cursor ->
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                val contentFilename = cursor.getString(
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                )
                viewCards.add(ViewCard(contentUri, contentFilename))
            }
            _cardUris.value = viewCards
        }
    }
}