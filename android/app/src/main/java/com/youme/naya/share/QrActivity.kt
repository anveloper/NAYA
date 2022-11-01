package com.youme.naya.share

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.youme.naya.BaseActivity
import com.youme.naya.R
import com.youme.naya.ui.theme.AndroidTheme
import com.youme.naya.ui.theme.NeutralMedium
import com.youme.naya.ui.theme.fonts
import com.youme.naya.widgets.share.CircleWaveComp
import com.youme.naya.widgets.share.ShareHeader
import com.youme.naya.widgets.share.ShareInfo

class QrActivity : BaseActivity(TransitionMode.HORIZON) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = LocalContext.current as? Activity
            val contentUrl = intent.getStringExtra("contentUrl") ?: ""
            val contents = "https://k7b104.p.ssafy.io/$contentUrl"
            val bitmap = generateBitmapQRCode(contents)

            AndroidTheme() {
                QrShareScreen(contents.toUri(), bitmap) {
                    activity?.finish()
                }
            }
        }
    }
}

private fun generateBitmapQRCode(contents: String): Bitmap {
    val barcodeEncoder = BarcodeEncoder()
    return barcodeEncoder.encodeBitmap(contents, BarcodeFormat.QR_CODE, 520, 520)
}

@Composable
fun QrShareScreen(
    contents: Uri,
    bitmap: Bitmap,
    onFinish: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        // 배경 컴포넌트
        CircleWaveComp()
        // QR 컨텐츠
        ShareQr(contents, bitmap)
        ShareInfo(R.drawable.ic_share_qr, "\n아래의 QR코드를 스캔해서\nNaya 카드를 받으세요")
        Column(Modifier.fillMaxSize(), Arrangement.SpaceBetween, Alignment.CenterHorizontally) {
            ShareHeader { onFinish() }
            Spacer(Modifier.height(280.dp))
            Text(text = "QR코드를 터치하면 웹으로 이동할 수 있습니다.", color = NeutralMedium, fontFamily = fonts)
            Spacer(Modifier.height(8.dp))
        }
    }
}


@Composable
fun ShareQr(
    contents: Uri,
    bitmap: Bitmap,
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { }
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        IconButton(onClick = {
            launcher.launch(Intent(Intent.ACTION_VIEW, contents))
        }, Modifier.shadow(6.dp)) {
            Image(bitmap.asImageBitmap(), null)
        }
    }
}


@Preview(
    name = "naya Project share QR", showBackground = true, showSystemUi = true
)
@Composable
fun QrSharePreview() {
    val contents = "https://www.google.com"
    QrShareScreen(
        contents.toUri(),
        generateBitmapQRCode(contents)
    ) { Log.i("ShareActivity", "test") }
}
