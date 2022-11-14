package com.youme.naya.custom

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Lens
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraView(
    executor: Executor,
    lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    setImageBitmap: (Bitmap) -> Unit,
) {
    // 1
//    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    // 2
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    // 3
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        IconButton(
            modifier = Modifier.padding(bottom = 20.dp),
            onClick = {
                takePhoto(
                    owner = lifecycleOwner,
                    cameraSelector = lensFacing,
                    imageCapture = imageCapture,
                    executor = executor,
                    setImageBitmap = setImageBitmap
                )
            },
            content = {
                Icon(
                    imageVector = Icons.Sharp.Lens,
                    contentDescription = "Take picture",
                    tint = Color.White,
                    modifier = Modifier
                        .size(56.dp)
                        .padding(1.dp)
                        .border(1.dp, Color.White, CircleShape)
                )
            }
        )
    }
}

private fun takePhoto(
    owner: LifecycleOwner,
    cameraSelector: Int,
    imageCapture: ImageCapture,
    executor: Executor,
    setImageBitmap: (Bitmap) -> Unit
) {

    imageCapture.takePicture(
        executor,
        object : ImageCapture.OnImageCapturedCallback(), ImageCapture.OnImageSavedCallback {

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
                    Log.i("CameraX", image.imageInfo.timestamp.toString())
                    setImageBitmap(resultImage)
                }
            }

            private suspend fun imageProxyToBitmap(image: ImageProxy): Bitmap =
                withContext(owner.lifecycleScope.coroutineContext) {
                    val planeProxy = image.planes[0]
                    val buffer: ByteBuffer = planeProxy.buffer
                    val bytes = ByteArray(buffer.remaining())
                    buffer.get(bytes)
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
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

            fun rotateBitmap(bitmap: Bitmap, degree: Float): Bitmap {
                val matrix = Matrix()
                matrix.postRotate(degree)
                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.i("CameraX Error", "$exception")
            }

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                Log.i("CameraX", "onCaptureSuccess: Uri  ${outputFileResults.savedUri}")
            }
        })

}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }
