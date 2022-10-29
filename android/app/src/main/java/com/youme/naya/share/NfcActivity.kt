package com.youme.naya.share

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.BaseActivity
import com.youme.naya.R
import com.youme.naya.ui.theme.AndroidTheme
import com.youme.naya.ui.theme.NeutralGray

class NfcActivity : BaseActivity(TransitionMode.HORIZON) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = LocalContext.current as? Activity
            AndroidTheme() {
                NfcShareScreen() {
                    activity?.finish()
                }
            }
        }
    }
}





// view
@Composable
fun NfcShareScreen(
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    Box(Modifier.fillMaxSize()) {
        CircleWaveComp()
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            IconButton(onClick = {
                onFinish()
            }) {
                Icon(
                    Icons.Outlined.Close, "move to share", tint = Color(0xFFCED3D6)
                )
            }
        }
        Box(Modifier.matchParentSize(), Alignment.Center) {
            Box(
                Modifier
                    .width(155.dp)
                    .height(280.dp)
                    .shadow(4.dp)
                    .background(PrimaryGradientBrushH), Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.home_logo_text),
                    null,
                    Modifier.rotate(270f),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
        Column(
            Modifier
                .matchParentSize(), Arrangement.SpaceEvenly, Alignment.CenterHorizontally
        ) {
            Column(
                Modifier
                    .fillMaxWidth(), Arrangement.Top, Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(R.drawable.ic_share_nfc), null)
                Text(
                    text = "NFC로 카드를 전송 할게요\n근처의 상대방을 찾고 있어요",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = NeutralGray
                )
            }
            Spacer(Modifier.height(280.dp))
            Spacer(Modifier.height(60.dp))
        }

    }
}

@Preview(
    name = "naya Project share NFC", showBackground = true, showSystemUi = true
)
@Composable
fun NfcSharePreview() {
    NfcShareScreen() { Log.i("ShareActivity", "test") }
}
