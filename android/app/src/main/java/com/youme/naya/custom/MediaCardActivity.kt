package com.youme.naya.custom

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Cached
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.youme.naya.BaseActivity
import com.youme.naya.ui.theme.*
import com.youme.naya.utils.saveCardImage
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val LENS_BACK = CameraSelector.LENS_FACING_BACK
private const val LENS_FRONT = CameraSelector.LENS_FACING_FRONT

class MediaCardActivity : BaseActivity(TransitionMode.HORIZON) {

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = LocalContext.current as? Activity

            val savedImgAbsolutePath = intent.getStringExtra("savedImgAbsolutePath")
            val tmpImage: Bitmap? = BitmapFactory.decodeFile(savedImgAbsolutePath)
            AndroidTheme() {
                MediaCardScreen(tmpImage, cameraExecutor,
                    // 액티비티 기준 커스텀 사진 저장 함수
                    { bitmap ->
                        saveCardImage(baseContext, bitmap)
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
        cameraExecutor = Executors.newSingleThreadExecutor()
    }
}

@Composable
fun MediaCardScreen(
    tmpImage: Bitmap?,
    cameraExecutor: ExecutorService,
    createNayaCardFile: (Bitmap) -> Unit,
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val density = LocalDensity.current
    val view = LocalView.current

    val captureController = rememberCaptureController()

    var (tmpBitmap, setTmpBitmap) = rememberSaveable {
        mutableStateOf<Bitmap?>(tmpImage)
    }
    val cameraX = CustomCameraX(context, lifecycleOwner)

    val (lensFacing, setLensFacing) = remember { mutableStateOf(LENS_FRONT) }

    fun changeLensFacing() {
        if (lensFacing == LENS_BACK) {
            setLensFacing(LENS_FRONT)
        } else {
            setLensFacing(LENS_BACK)
        }
    }


    Box(Modifier.fillMaxSize()) {
        if (tmpBitmap == null) {
            CameraView(
                cameraExecutor, lensFacing
            ) { bitmap ->
                setTmpBitmap(bitmap)
            }
//            CustomCamera(context, cameraX) {
//                cameraX.capturePhoto() { bitmap ->
//                    setTmpBitmap(bitmap)
//                }
//            }
        } else {
            Capturable(controller = captureController, onCaptured = { bitmap, error ->
                if (bitmap != null) {
                    val result = getCardSection(bitmap.asAndroidBitmap(), density, view)
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
        if (tmpBitmap == null) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(end = 80.dp, bottom = 24.dp), Alignment.BottomEnd
            ) {
                IconButton(onClick = { changeLensFacing() }) {
                    Icon(
                        Icons.Outlined.Cached, null,
                        Modifier
                            .size(32.dp)
                            .padding(1.dp)
                            .border(1.dp, Color.White, CircleShape),
                        NeutralWhite
                    )
                }
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