package com.youme.naya.custom

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.youme.naya.BaseActivity
import com.youme.naya.ui.theme.AndroidTheme
import com.youme.naya.ui.theme.NeutralLight
import com.youme.naya.ui.theme.PrimaryBlue
import com.youme.naya.ui.theme.fonts
import com.youme.naya.widgets.home.CardListViewModel
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class MediaCardActivity : BaseActivity(TransitionMode.HORIZON) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = LocalContext.current as? Activity
            val viewModel = viewModel<CardListViewModel>()
            AndroidTheme() {
                MediaCardScreen(
                    // 액티비티 기준 커스텀 사진 저장 함수
                    { bitmap ->
                        createNayaCardFile(bitmap)
                        intent.putExtra("Custom Exit", 1)
                        setResult(RESULT_OK, intent)
                        activity?.finish()
                    },
                    // 액티비티 취소
                    {
                        intent.putExtra("finish", 0)
                        setResult(RESULT_OK, intent)
                        activity?.finish()
                    }
                )
            }
        }
    }

    private fun createNayaCardFile(bitmap: Bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveImageOnAboveAndroidQ(bitmap)
            Toast.makeText(baseContext, "이미지 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            val writePermission = ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

            if (writePermission == PackageManager.PERMISSION_GRANTED) {
                saveImageOnUnderAndroidQ(bitmap)
                Toast.makeText(baseContext, "이미지 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val requestExternalStorageCode = 1

                val permissionStorage = arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                ActivityCompat.requestPermissions(
                    this,
                    permissionStorage,
                    requestExternalStorageCode
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageOnAboveAndroidQ(bitmap: Bitmap) {
        val fileName = "NAYA-MEDIA-" + System.currentTimeMillis().toString() + ".png"

        val contentValues = ContentValues()
        contentValues.apply {
            put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/NAYA")
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        try {
            if (uri != null) {
                val image = contentResolver.openFileDescriptor(uri, "w", null)

                if (image != null) {
                    val fos = FileOutputStream(image.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    fos.close()

                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    Log.i("Media Card Saved", uri.toString())
                    contentResolver.update(uri, contentValues, null, null)
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun saveImageOnUnderAndroidQ(bitmap: Bitmap) {
        val fileName = "NAYA-MEDIA-" + System.currentTimeMillis().toString() + ".png"
        val externalStorage = Environment.getExternalStorageDirectory().absolutePath
        val path = "$externalStorage/DCIM/imageSave"
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

            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@Composable
fun MediaCardScreen(
    createNayaCardFile: (Bitmap) -> Unit,
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val density = LocalDensity.current
    val view = LocalView.current

    val captureController = rememberCaptureController()

    var (tmpBitmap, setTmpBitmap) = rememberSaveable {
        mutableStateOf<Bitmap?>(null)
    }
    var (resultBitmap, setResultBitmap) = rememberSaveable {
        mutableStateOf<Bitmap?>(null)
    }
    val cameraX = CustomCameraX(context, lifecycleOwner)

    Box(Modifier.fillMaxSize()) {
        if (tmpBitmap == null) {
            CustomCamera(context, cameraX) {
                cameraX.capturePhoto() { bitmap ->
                    setTmpBitmap(bitmap)
                }
            }
        } else {
            Capturable(controller = captureController, onCaptured = { bitmap, error ->
                if (bitmap != null) {
                    val result = getCardSection(bitmap.asAndroidBitmap(), density, view)
//                    setResultBitmap(result)
//                    setTmpBitmap(result) // 화면 출력 확인
                    createNayaCardFile(result)
                }
                if (error != null) {
                    Toast.makeText(context, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
                    onFinish()
                }
            }) {
                CustomImage(tmpBitmap)
            }
        }
        Row(Modifier.fillMaxSize(), Arrangement.SpaceBetween, Alignment.Top) {
            TextButton(
                onClick = {
                    onFinish()
                }) {
                Icon(
                    Icons.Outlined.ArrowBackIos,
                    "move to start",
                    tint = NeutralLight
                )
                Text(text = "돌아가기", fontFamily = fonts, color = NeutralLight)
            }
            if (tmpBitmap != null) {
                TextButton(
                    onClick = {
                        captureController.capture()
                    }) {
                    Text(text = "완료", fontFamily = fonts, color = PrimaryBlue)
                    Icon(
                        Icons.Outlined.ArrowForwardIos,
                        "move to start",
                        tint = PrimaryBlue
                    )
                }
            } else Spacer(Modifier.width(4.dp))
        }
    }
}


private fun getCardSection(bitmap: Bitmap, density: Density, view: View): Bitmap {
    val centerX = view.width / 2
    val centerY = view.height / 2
    val offsetX = with(density) { 300.dp.toPx() }.toInt()
    val offsetY = with(density) { 540.dp.toPx() }.toInt()
    Log.i(
        "Where",
        "${centerX - offsetX / 2} ${centerY - offsetY / 2} $offsetX $offsetY"
    )
    return Bitmap.createBitmap(
        bitmap,
        centerX - offsetX / 2,
        centerY - offsetY / 2,
        offsetX,
        offsetY,
    )
}


@Preview(
    name = "Media Card Custom Screen",
    showSystemUi = true,
    showBackground = true

)
@Composable
fun MediaCardCustomPreview() {
//    MediaCardScreen() {}
}