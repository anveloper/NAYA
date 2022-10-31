package com.youme.naya.widgets.share.nfc

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.R
import com.youme.naya.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NfcSendingResult(
    result: SendingResult,
    onFinish: () -> Unit,
    reSearch: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(480.dp)
            .background(NeutralWhite)
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ResultContent(result)
            Spacer(Modifier.height(48.dp))
            Row(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterVertically) {
                TextButton(
                    onClick = { onFinish() },
                    Modifier
                        .width(120.dp)
                        .height(48.dp)
                        .border(1.dp, SecondaryLightBlue, RoundedCornerShape(8.dp))
                        .shadow(2.dp, RoundedCornerShape(8.dp))
                        .background(PrimaryLight, RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = "돌아가기",
                        color = PrimaryBlue,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.width(12.dp))
                TextButton(
                    onClick = { reSearch() },
                    Modifier
                        .width(120.dp)
                        .height(48.dp)
                        .shadow(2.dp, RoundedCornerShape(8.dp))
                        .background(PrimaryBlue, RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = "다시 보내기",
                        color = PrimaryLight,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}


@Composable
fun ResultContent(result: SendingResult) {
    val flag = result == SendingResult.Success
    Column(
        Modifier
            .fillMaxWidth()
            .height(220.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .width(120.dp)
                .height(120.dp)
                .border(
                    4.dp,
                    (if (flag) PrimaryGradientBrush else SystemRedGradientBrush),
                    CircleShape
                )
                .background((if (flag) SecondaryLightBlue else NeutralWhite), CircleShape),
            Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_share_nfc),
                contentDescription = null,
                colorFilter = ColorFilter.tint(NeutralDarkGray)
            )
        }
        Spacer(Modifier.height(24.dp))
        Row(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterVertically) {
            Image(painterResource(R.drawable.home_tab_naya), null)
            val text = if (flag) "전송이 완료되었습니다." else "전송에 실패했습니다."
            Text(text, fontSize = 20.sp, color = PrimaryDark)
            val img = if (flag) R.drawable.emoji_success else R.drawable.emoji_fail
            Image(painterResource(img), null)
        }

    }

}

enum class SendingResult {
    Idle,
    Fail,
    Success
}

@Preview(
    name = "naya Project share NFC", showBackground = true, showSystemUi = true
)
@Composable
fun NfcSendingResultPreview() {
    NfcSendingResult(SendingResult.Success, {}) { Log.i("ShareActivity", "test") }
}
