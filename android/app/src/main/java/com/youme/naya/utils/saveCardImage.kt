package com.youme.naya.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

fun saveCardImage(context: Context, bitmap: Bitmap, isBcard: Boolean = false): String? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val path = saveImageOnAboveAndroidQ(context, bitmap, isBcard)
        Toast.makeText(context, "이미지 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        return path
    } else {
        val writePermission = ActivityCompat.checkSelfPermission(
            context as Activity,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (writePermission == PackageManager.PERMISSION_GRANTED) {
            val path = saveImageOnUnderAndroidQ(context, bitmap, isBcard)
            Toast.makeText(context, "이미지 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            return path
        } else {
            val requestExternalStorageCode = 1

            val permissionStorage = arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(
                context,
                permissionStorage,
                requestExternalStorageCode
            )
        }
    }
    return null
}

@RequiresApi(Build.VERSION_CODES.Q)
private fun saveImageOnAboveAndroidQ(context: Context, bitmap: Bitmap, isBcard: Boolean): String? {
    val fileName = "NAYA-${if (isBcard) "BUSINESS" else "MEDIA"}-" + System.currentTimeMillis()
        .toString() + ".png"

    val contentValues = ContentValues()
    contentValues.apply {
        put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/NAYA")
        put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.IS_PENDING, 1)
    }

    val uri =
        context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    try {
        if (uri != null) {
            val image = context.contentResolver.openFileDescriptor(uri, "w", null)

            if (image != null) {
                val fos = FileOutputStream(image.fileDescriptor)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos.close()

                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                Log.i("Media Card Saved", uri.toString())
                context.contentResolver.update(uri, contentValues, null, null)

                image.close()

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


private fun saveImageOnUnderAndroidQ(context: Context, bitmap: Bitmap, isBcard: Boolean): String? {
    val fileName = "NAYA-${if (isBcard) "BUSINESS" else "MEDIA"}-" + System.currentTimeMillis()
        .toString() + ".png"
    val externalStorage = Environment.getExternalStorageDirectory().absolutePath
    val path = "$externalStorage/DCIM/NAYA"
    val dir = File(path)

    if (dir.exists().not()) {
        dir.mkdirs()
    }

    try {
        val fileItem = File("$dir/$fileName")
        fileItem.createNewFile()
        //0KB 파일 생성.

        val fos = FileOutputStream(fileItem)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.close()

        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))

        return fileItem.absolutePath
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}