package com.youme.naya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.youme.naya.camera.CameraX
import com.youme.naya.ui.theme.AndroidTheme

class MainActivity : ComponentActivity() {
    private var cameraX: CameraX = CameraX(this, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTheme {
                MainScreen()
//                CameraCompose(this, cameraX = cameraX) {
//                    if (allPermissionsGranted(this)) {
//                        cameraX.capturePhoto()
//                    }
//                }
            }
        }
    }

}

@Preview(
    name = "naya Project",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MainPreview() {
//    MainScreen(changeActivity=changeActivity())
}