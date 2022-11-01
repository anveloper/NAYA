package com.youme.naya.widgets.share.nfc

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.youme.naya.R
import com.youme.naya.ui.theme.*

@Composable
fun ShareNfcSearchFail(
    onFinish: () -> Unit,
    reSearch: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { reSearch() },
        buttons = {
            Box(
                Modifier
                    .width(280.dp)
                    .height(280.dp),
                Alignment.BottomCenter
            ) {
                Box(Modifier.fillMaxSize().zIndex(1.0f), Alignment.TopCenter) {
                    Image(painterResource(R.drawable.not_found_ghost), null)
                }

                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(213.dp)
                        .background(NeutralWhite, RoundedCornerShape(20.dp))
                        .padding(bottom = 20.dp),
                    Arrangement.Bottom,
                    Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "상대방을 찾지 못했어요",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "NFC Tagging에 실패하였습니다.\n근처에 사용자가 있는 지 확인해주세요.",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        color = NeutralLight
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(Modifier.width(280.dp), Arrangement.Center, Alignment.CenterVertically) {
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
                        Spacer(Modifier.width(8.dp))
                        TextButton(
                            onClick = { reSearch() },
                            Modifier
                                .width(120.dp)
                                .height(48.dp)
                                .shadow(2.dp, RoundedCornerShape(8.dp))
                                .background(PrimaryBlue, RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = "다시 찾기",
                                color = PrimaryLight,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        },

        backgroundColor = Color.Transparent,
        shape = RoundedCornerShape(20.dp)
    )

}

@Preview(
    name = "NFC search fail", showBackground = true, showSystemUi = true
)
@Composable
fun NfcSearchFailPreview() {
    ShareNfcSearchFail({}) {}
}
