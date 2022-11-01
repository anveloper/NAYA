package com.youme.naya.widgets.share.nfc

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.R
import com.youme.naya.ui.theme.NeutralGray

@Composable
fun ShareNfcContent() {
    Column(
        Modifier
            .fillMaxSize(), Arrangement.SpaceEvenly, Alignment.CenterHorizontally
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