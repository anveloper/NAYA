package com.youme.naya.share

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.BaseActivity
import com.youme.naya.R
import com.youme.naya.ui.theme.*


class ShareActivity : BaseActivity(TransitionMode.VERTICAL) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = LocalContext.current as? Activity
            AndroidTheme() {
                ShareScreen() {
                    activity?.finish()
                }
            }
        }
    }
}

private val ShareContainerModifier = Modifier
    .fillMaxSize()
    .background(color = Color(0xFFFFFFFF))

private val ShareTitleModifier = Modifier
    .fillMaxWidth()
    .height(64.dp)

val PrimaryGradientBrush = Brush.verticalGradient(
    listOf(
        Color(0xFF055EEA),
        Color(0xFF0891F2)
    )
)
val PrimaryGradientBrushH = Brush.horizontalGradient(
    listOf(
        Color(0xFF055EEA),
        Color(0xFF0891F2)
    )
)

@Composable
fun ShareScreen(
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    Column(ShareContainerModifier, Arrangement.SpaceBetween, Alignment.CenterHorizontally) {
        Box(
            ShareTitleModifier,
            contentAlignment = Alignment.Center
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                IconButton(
                    onClick = {
                        onFinish()
                    }) {
                    Icon(
                        Icons.Outlined.ArrowBackIos,
                        "move to start",
                        tint = Color(0xFFCED3D6)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.home_tab_naya),
                    contentDescription = null
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    color = Color(0xFF122045),
                    fontSize = 16.sp,
                    text = "공유하기"
                )
            }
        }
        ShareTextButton(
            R.drawable.ic_share_nfc,
            "NFC 공유",
            "NFC를 이용하여 근처 사용자에게 카드를 보내세요"
        ) {
            context.startActivity(Intent(context, NfcActivity::class.java))
        }
        ShareTextButton(
            R.drawable.ic_share_beacon,
            "어플 공유",
            "Naya 사용자끼리 바로 카드를 보낼 수 있어요"
        ) {
            // 비콘 실행 로직
        }
        ShareTextButton(
            R.drawable.ic_share_qr,
            "QR코드 공유",
            "Naya 카드 고유의 QR코드를 생성해서 공유하세요"
        ) {
            // QR코드 생성
        }
        Column(
            Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterHorizontally
        ) {
            Text(
                color = PrimaryDark,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                text = "SNS 공유"
            )
            Spacer(Modifier.height(8.dp))
            Row() {
                ShareIconButton(R.drawable.ic_share_sns_kakao, "카카오톡") {
                    // 카카오톡 공유로직
                }
                ShareIconButton(R.drawable.ic_share_sns_twitter, "트위터") {
                    // 트위터 공유로직
                }
                ShareIconButton(R.drawable.ic_share_sns_facebook, "페이스북") {
                    // 페이스북 공유로직
                }
                ShareIconButton(R.drawable.ic_share_sns_mail, "메일") {
                    // 메일 공유로직
                }
            }
        }
        TextButton(
            modifier = Modifier
                .width(280.dp)
                .height(48.dp)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
                .background(color = PrimaryLight, shape = RoundedCornerShape(12.dp)),
            onClick = { /*TODO*/ }) {
            Text(
                color = PrimaryBlue,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp, text = "이미지 다운로드"
            )
        }
        Spacer(Modifier.height(4.dp))
    }
}


@Composable
fun ShareTextButton(
    imageId: Int,
    title: String,
    content: String,
    fn: () -> Unit
) {
    TextButton(modifier = Modifier
        .width(280.dp)
        .height(108.dp)
        .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
        .background(color = PrimaryLight, shape = RoundedCornerShape(12.dp)),
        onClick = { fn() }) {
        Row(
            Modifier.fillMaxSize(),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(imageId),
                contentDescription = ""
            )
            Spacer(Modifier.width(4.dp))
            Column(Modifier.padding(8.dp)) {
                Text(
                    color = PrimaryDark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    text = title
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    color = NeutralGray,
                    fontSize = 12.sp,
                    text = content
                )
            }
        }
    }
}

@Composable
fun ShareIconButton(
    imageId: Int,
    title: String,
    fn: () -> Unit
) {
    Column(
        Modifier.padding(horizontal = 4.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(24.dp))
                .background(brush = PrimaryGradientBrush, shape = RoundedCornerShape(24.dp)),
            onClick = { fn() }
        ) {
            Image(
                painterResource(imageId),
                contentDescription = ""
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            color = Color(0xFFA1ACB3),
            fontSize = 12.sp,
            text = title
        )
    }
}


@Preview(
    name = "naya Project share",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun sharePreview() {
    ShareScreen() { Log.i("ShareActivity", "test") }
}
