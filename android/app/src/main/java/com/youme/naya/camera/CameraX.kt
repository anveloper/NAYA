package com.youme.naya.camera


import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.ByteBuffer

class CameraX(
    private var context: Context,
    private var owner: LifecycleOwner
) {
    private var imageCapture: ImageCapture? = null
    private var cameraSelector = CameraSelector.LENS_FACING_FRONT

    fun startCameraPreviewView(): PreviewView {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        val previewView = PreviewView(context)
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }
        imageCapture = ImageCapture.Builder().build()

        val camSelector =
            CameraSelector.Builder().requireLensFacing(cameraSelector).build()
        try {
            cameraProviderFuture.get().bindToLifecycle(
                owner,
                camSelector,
                preview,
                imageCapture
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return previewView
    }

    fun capturePhoto(setImageBitmap: (Bitmap) -> Unit) = owner.lifecycleScope.launch {
        val imageCapture = imageCapture ?: return@launch
        imageCapture.takePicture(ContextCompat.getMainExecutor(context), object :
            ImageCapture.OnImageCapturedCallback(), ImageCapture.OnImageSavedCallback {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                owner.lifecycleScope.launch {
                    val resizedImage = resizeBitmap(1024, imageProxyToBitmap(image))
                    val rotatedImage =
                        rotateBitmap(resizedImage, image.imageInfo.rotationDegrees * 1.0f)
                    val resultImage =
                        if (cameraSelector == CameraSelector.LENS_FACING_FRONT)
                            inverseBitmap(rotatedImage)
                        else rotatedImage
//                    saveMediaToStorage(
//                        resultImage,
//                        System.currentTimeMillis().toString()
//                    )
                    Log.i("CameraX", image.imageInfo.timestamp.toString())
                    setImageBitmap(resultImage)
                }
            }

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                Log.i("CameraX", "onCaptureSuccess: Uri  ${outputFileResults.savedUri}")
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.i("CameraX", "onCaptureSuccess: onError")
            }
        })
    }

    private suspend fun imageProxyToBitmap(image: ImageProxy): Bitmap =
        withContext(owner.lifecycleScope.coroutineContext) {
            val planeProxy = image.planes[0]
            val buffer: ByteBuffer = planeProxy.buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

    private suspend fun saveMediaToStorage(bitmap: Bitmap, name: String) {
        withContext(IO) {
            val filename = "NAYA_$name.jpg"
            var fos: OutputStream? = null
            var tmpBitmap = bitmap
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                context.contentResolver?.also { resolver ->
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(
                            MediaStore.MediaColumns.RELATIVE_PATH,
                            Environment.DIRECTORY_DCIM
                        )
                    }
                    val imageUri: Uri? =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    var degree = 0.0f
                    if (imageUri != null) {
//                        degree = getDegree(imageUri, filename)
                    }
                    tmpBitmap = rotateBitmap(bitmap, degree)
                    fos = imageUri?.let {
                        with(resolver) { openOutputStream(it) }
                    }
                }
            } else {
                val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                val image = File(imagesDir, filename).also { fos = FileOutputStream(it) }
                Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
                    mediaScanIntent.data = Uri.fromFile(image)
                    context.sendBroadcast(mediaScanIntent)
                }
            }
            fos?.use {
                val success = async(IO) {
                    tmpBitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                }
                if (success.await()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "성공적으로 저장되었습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    fun resizeBitmap(targetWidth: Int, source: Bitmap): Bitmap {
        val ratio = targetWidth.toDouble() / source.width.toDouble()
        val targetHeight = (source.height * ratio).toInt()
        return Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false)
    }

    fun inverseBitmap(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.setScale(-1.0f, 1.0f)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun getDegree(uri: Uri, source: String): Float {
        var exif: ExifInterface? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val photoUri = MediaStore.setRequireOriginal(uri)
            val stream = context.contentResolver.openInputStream(photoUri)
            exif = ExifInterface(stream!!)
        } else {
            exif = ExifInterface(source)
        }
        var degree = 0
        when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
            ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
            ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
        }
        return degree.toFloat()
    }

    fun rotateBitmap(bitmap: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}