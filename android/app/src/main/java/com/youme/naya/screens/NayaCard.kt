package com.youme.naya.screens


import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.youme.naya.ocr.DocumentScannerActivity
import com.youme.naya.share.NfcActivity

@Composable
fun NayaCardScreen(
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            Log.i("TEST Result", it.resultCode.toString())
        }
    // 이 Row 삭제 하면 됩니다
    Row(
        modifier = Modifier
            .fillMaxSize(), Arrangement.SpaceEvenly, Alignment.CenterVertically
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
    // 여기까지




}

@Preview
@Composable
fun NayaCardScreenPreview() {
//    NayaCardScreen()
}
