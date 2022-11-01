package com.youme.naya.widgets.share

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.R
import com.youme.naya.share.QrActivity
import com.youme.naya.share.ShareIconButton
import com.youme.naya.share.ShareTextButton
import com.youme.naya.ui.theme.PrimaryBlue
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.PrimaryLight
import com.youme.naya.ui.theme.fonts

@Composable
fun ShareExtra() {

    Column(
        Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterHorizontally
    ) {
        Text(
            color = PrimaryDark,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            text = "SNS 공유",
            fontFamily = fonts

        )
        Spacer(Modifier.height(8.dp))
        Row() {
            ShareIconButton(R.drawable.ic_share_sns_kakao, "카카오톡") {
                // 카카오톡 공유로직
            }
            Spacer(Modifier.width(4.dp))
            ShareIconButton(R.drawable.ic_share_sns_twitter, "트위터") {
                // 트위터 공유로직
            }
            Spacer(Modifier.width(4.dp))
            ShareIconButton(R.drawable.ic_share_sns_facebook, "페이스북") {
                // 페이스북 공유로직
            }
            Spacer(Modifier.width(4.dp))
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
            fontFamily = fonts,
            fontSize = 14.sp, text = "이미지 다운로드"
        )
    }
    Spacer(Modifier.height(4.dp))
}