package com.youme.naya.share

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import com.youme.naya.ui.theme.AndroidTheme


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

private val PrimaryGradientBrush = Brush.verticalGradient(
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
//                        context.startActivity(Intent(context, MainActivity::class.java))
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
            "휴대폰에 내장된 NFC를 사용하여 근처 사용자에게 카드를 보내세요"
        ) {
            context.startActivity(Intent(context, NfcActivity::class.java))
        }
        ShareTextButton(
            R.drawable.ic_share_beacon,
            "어플 공유",
            "Naya 어플이 설치되어 있다면 바로 카드를 보낼 수 있어요"
        ) {}
        ShareTextButton(
            R.drawable.ic_share_qr,
            "QR코드 공유",
            "상대방이 내 QR코드를 스캔하면\n" +
                    "빠르게 카드를 공유할 수 있어요"
        ) {}
        Text(text = "SNS 공유")
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
        TextButton(
            modifier = Modifier
                .width(280.dp)
                .height(60.dp)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
                .background(brush = PrimaryGradientBrush, shape = RoundedCornerShape(12.dp)),
            onClick = { /*TODO*/ }) {
            Text(
                color = Color(0xFFFEFEFE),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp, text = "이미지 다운로드"
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
    TextButton(onClick = { fn() }) {
        Box(
            Modifier
                .width(288.dp)
                .height(112.dp)
        ) {
            Image(painterResource(R.drawable.btn_share_sq), contentDescription = "")
            Row(
                Modifier
                    .matchParentSize()
                    .padding(12.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(imageId),
                    contentDescription = ""
                )
                Spacer(Modifier.width(8.dp))
                Column() {
                    Text(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        text = title
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        color = Color.White,
                        fontSize = 12.sp,
                        text = content
                    )
                }
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
        IconButton(onClick = { fn() }) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(painterResource(R.drawable.ic_bg_circle), contentDescription = "")
                Image(
                    painterResource(imageId),
                    contentDescription = ""
                )
            }
        }
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
    ShareScreen() {}
}
