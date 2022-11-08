package com.youme.naya.screens


import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.youme.naya.ocr.DocumentScannerActivity
import com.youme.naya.share.NfcActivity
import com.youme.naya.share.ShareActivity

@Composable
fun NayaCardScreen(
    navController: NavHostController
) {
    val (cameraOn, setCameraOn) = rememberSaveable { mutableStateOf(false) }
    val (galleryOn, setGalleryOn) = rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    val activity = context as? Activity
    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            Log.i("TEST Result", it.resultCode.toString())
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Blue,
                        Color.DarkGray
                    )
                ),
                alpha = 0.4f
            ),
    ) {

        Button(onClick = {
            context.startActivity(Intent(context, NfcActivity::class.java))
        }) {
            Text(text = "nfc", fontSize = 16.sp)
        }
        Button(onClick = {
            launcher.launch(Intent(activity, DocumentScannerActivity::class.java))
        }) {
            Text(text = "ocr", fontSize = 16.sp)
        }

    }
}

@Preview
@Composable
fun NayaCardScreenPreview() {
//    NayaCardScreen()
}
