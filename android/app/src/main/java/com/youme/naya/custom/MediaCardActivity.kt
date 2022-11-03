package com.youme.naya.custom

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.compose.setContent
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youme.naya.BaseActivity
import com.youme.naya.ui.theme.AndroidTheme
import com.youme.naya.ui.theme.NeutralLight
import com.youme.naya.ui.theme.PrimaryBlue
import com.youme.naya.ui.theme.fonts
import com.youme.naya.utils.dp2px
import dev.shreyaspatil.capturable.controller.rememberCaptureController


class MediaCardActivity : BaseActivity(TransitionMode.HORIZON) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = LocalContext.current as? Activity
            AndroidTheme() {
                MediaCardScreen() {
                    intent.putExtra("finish", 0)
                    setResult(RESULT_OK, intent)
                    activity?.finish()
                }
            }
        }
    }
}

@Composable
fun MediaCardScreen(
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val view = LocalView.current

    val captureController = rememberCaptureController()

    var (tmpBitmap, setTmpBitmap) = rememberSaveable {
        mutableStateOf<Bitmap?>(null)
    }
    var (resultBitmap, setResultBitmap) = rememberSaveable {
        mutableStateOf<ImageBitmap?>(null)
    }
    val cameraX = CustomCameraX(context, lifecycleOwner)

    Box(Modifier.fillMaxSize()) {
        if (tmpBitmap == null) {
            CustomCamera(context, cameraX) {
                cameraX.capturePhoto() { bitmap ->
                    Log.i("Capture", "Take a picture")
                    setTmpBitmap(bitmap)
                }
            }
        } else {
            CustomImage(tmpBitmap, context, captureController, setTmpBitmap, onFinish)
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
                        tint = NeutralLight
                    )
                }
            } else Spacer(Modifier.width(4.dp))
        }
    }
}

private fun getCardSection(view: View, bitmap: Bitmap): Bitmap {
    val centerX = view.width / 2
    val centerY = view.height / 2
    val offsetX = dp2px(300)
    val offsetY = dp2px(540)
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
    MediaCardScreen() {}
}