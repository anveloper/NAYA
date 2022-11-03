package com.youme.naya.custom

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import com.youme.naya.BaseActivity
import com.youme.naya.ui.theme.AndroidTheme
import com.youme.naya.ui.theme.NeutralLight
import com.youme.naya.ui.theme.fonts

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
    var (tmpBitmap, setTmpBitmap) = rememberSaveable {
        mutableStateOf<Bitmap?>(null)
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
            CustomImage(tmpBitmap)
        }
        Column(Modifier.fillMaxSize(), Arrangement.Top, Alignment.Start) {
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
        }
    }
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