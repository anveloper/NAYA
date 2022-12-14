package com.youme.naya.widgets.home

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class ViewCard(id: Long, uri: Uri, filename: String) {
    val id = id
    val uri = uri
    val filename = filename
}

class VideoCard(id: Long, uri: Uri, filename: String) {
    val id = id
    val uri = uri
    val filename = filename
}

class CardListViewModel(application: Application) : AndroidViewModel(application) {
    private val _viewCards = mutableStateOf(emptyList<ViewCard>())
    val viewCards: State<List<ViewCard>> = _viewCards

    fun fetchNayaCards() {
        fetchCards("NAYA")
    }

    fun fetchNayaCardsInNuya() {
        fetchCards("NUYA")
    }

    fun fetchBusinessCardsInNuya() {
        fetchCards("NUYA", true)
    }

    fun fetchCards(prefix: String, isBusinessCard: Boolean = false) {
        val viewCards = mutableListOf<ViewCard>()
        getApplication<Application>().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Files.FileColumns.TITLE + " LIKE ?",
            arrayOf("$prefix-${if (isBusinessCard) "BUSINESS" else "MEDIA"}-%"),
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
                viewCards.add(ViewCard(id, contentUri, contentFilename))
            }
            _viewCards.value = viewCards
        }
    }


    // video
    private val _videoCard = mutableStateOf<VideoCard?>(null)
    val videoCard = _videoCard

    fun fetchVideoCard() {
        val videoCard = mutableStateOf<VideoCard?>(null)
        Log.i("CARD VM S", "FETCH VIDEO")
        getApplication<Application>().contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Files.FileColumns.TITLE + " LIKE ?",
            arrayOf("NAYA-VIDEO-%"),
            "${MediaStore.Video.VideoColumns.DATE_TAKEN} DESC"
        )?.use { cursor ->
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)

            while (cursor.moveToNext()) {
                Log.i("CARD VM S", "CURSOR")
                if (videoCard.value != null) break
                val id = cursor.getLong(idIndex)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                val contentFilename = cursor.getString(
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                )
                Log.i("CARD VM", contentUri.toString())
                videoCard.value = VideoCard(id, contentUri, contentFilename)
            }
            _videoCard.value = videoCard.value
        }
    }
    // video support

    private val _supportCard = mutableStateOf<ViewCard?>(null)
    val supportCard = _supportCard

    fun fetchSupportCard() {
        val supportCard = mutableStateOf<ViewCard?>(null)
        getApplication<Application>().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Files.FileColumns.TITLE + " LIKE ?",
            arrayOf("NAYA-SUPPORT-%"),
            "${MediaStore.Images.ImageColumns.DATE_TAKEN} DESC"
        )?.use { cursor ->
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                if (supportCard != null) break
                val id = cursor.getLong(idIndex)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                val contentFilename = cursor.getString(
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                )
                supportCard.value = ViewCard(id, contentUri, contentFilename)
            }
            _supportCard.value = supportCard.value
        }
    }
}