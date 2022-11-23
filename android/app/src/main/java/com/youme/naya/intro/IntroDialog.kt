package com.youme.naya.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.youme.naya.R
import com.youme.naya.ui.theme.NeutralLight
import com.youme.naya.ui.theme.NeutralWhite
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.fonts

@OptIn(ExperimentalPagerApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun IntroDialog(
    onFinish: () -> Unit,
) {
    val information = listOf<IntroItem>(
        // 순서는 수정예정
        IntroItem("공유하기", "HOME에서 전송버튼을 누르거나\n카드를 위로 SWIPE", R.drawable.intro003),
        IntroItem(
            "기존 명함 등록하기",
            "NUYA 혹은 Business탭에서\n기존 명함을 등록 할 수 있어요",
            R.drawable.intro002
        ),
        IntroItem("Naya 카드 만들기", "NAYA에서 버튼을 눌러 사진을 찍거나\n갤러리에서 등록하세요", R.drawable.intro001),

        IntroItem("일정등록", "캘린더에 일정을 등록하고\n알림을 받을 수 있어요", R.drawable.intro004)
    )

    AlertDialog(onDismissRequest = { onFinish() }, buttons = {
        Column(Modifier.padding(0.dp), Arrangement.Center, Alignment.CenterHorizontally) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(460.dp)
                    .background(NeutralWhite, RoundedCornerShape(12.dp)),
                Alignment.Center
            ) {
                HorizontalPager(count = information.size, Modifier.fillMaxWidth(0.9f)) { index ->
                    val introItem = information[index]
                    Column(
                        Modifier.fillMaxWidth(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = introItem.title,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = PrimaryDark
                        )
                        Spacer(Modifier.height(16.dp))
                        Image(painterResource(introItem.image), null, Modifier.fillMaxWidth())
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = introItem.description,
                            fontFamily = fonts,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = PrimaryDark,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Box(Modifier.fillMaxWidth(), Alignment.CenterEnd) {
                TextButton(onClick = { onFinish() }) {
                    Icon(
                        Icons.Outlined.Check,
                        null,
                        Modifier.size(16.dp),
                        NeutralWhite
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "더이상 보지 않기",
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        color = NeutralWhite
                    )
                }
            }

        }
    }, backgroundColor = Color.Transparent)
}

data class IntroItem(
    val title: String,
    val description: String,
    val image: Int,
)