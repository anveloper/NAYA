package com.youme.naya.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore.Video
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


@RequiresApi(Build.VERSION_CODES.Q)
fun saveVideoRaw(context: Context, videoUri: Uri): String? {
    val path = saveImageOnAboveAndroidQ(context, videoUri)
    Toast.makeText(context, "영상이 저장이 시작되었습니다.", Toast.LENGTH_SHORT).show()
    return path
}

@RequiresApi(Build.VERSION_CODES.Q)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
private fun saveImageOnAboveAndroidQ(context: Context, videoUri: Uri): String? {
    val fileName = "NAYA-VIDEO-" + System.currentTimeMillis()
        .toString() + ".mp4"

    val contentValues = ContentValues()
    contentValues.apply {
        put(Video.Media.RELATIVE_PATH, "DCIM/NAYA")
        put(Video.Media.DISPLAY_NAME, fileName)
        put(Video.Media.MIME_TYPE, "video/mp4")
        put(Video.Media.IS_PENDING, 1)
    }

    val uri =
        context.contentResolver.insert(Video.Media.EXTERNAL_CONTENT_URI, contentValues)

    try {
        if (uri != null) {
            val input =
                context.contentResolver.openInputStream(videoUri)

            val video = context.contentResolver.openFileDescriptor(uri, "w", null)

            if (video != null) {
                val fos = FileOutputStream(video.fileDescriptor)

                val bytes = ByteArray(1024)
                var read = input?.read(bytes)!!
                while (read != -1) {
                    fos.write(bytes, 0, read)
                    read = input.read(bytes)
                }
                input.close()
                fos.close()

                contentValues.clear()
                contentValues.put(Video.Media.IS_PENDING, 0)
                Log.i("Video Card Saved", uri.toString())
                context.contentResolver.update(uri, contentValues, null, null)

                video.close()

                return convertUri2Path(context, uri)
            }
        }
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}
