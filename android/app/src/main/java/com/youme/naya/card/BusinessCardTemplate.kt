package com.youme.naya.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.ui.theme.*

@Composable
fun BusinessCardTemplate(
    name: String,
    engName: String,
    email: String,
    mobile: String,
    address: String,
    team: String,
    role: String,
    company: String,
    logo: String
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .aspectRatio(9f / 5f)
            .background(NeutralWhite)
            .shadow(2.dp)
            .drawBehind {
                val strokeWidth = 16f
                val y = size.height - strokeWidth / 2

                drawLine(
                    PrimaryBlue,
                    Offset(0f, y),
                    Offset(size.width + 48, y),
                    strokeWidth
                )
            }) {
        Text(
            name.ifEmpty { "이름" },
            fontSize = 16.sp,
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            color = NeutralDarkGray,
            modifier = Modifier
                .align(
                    Alignment.TopStart
                )
                .padding(top = 24.dp, start = 24.dp)
        )
        Text(
            engName.ifEmpty { "영어 이름" },
            fontSize = 11.sp,
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            color = NeutralMetal,
            modifier = Modifier
                .align(
                    Alignment.TopStart
                )
                .padding(top = 48.dp, start = 24.dp)
        )
        Text(
            email.ifEmpty { "이메일" },
            fontSize = 10.sp,
            fontFamily = fonts,
            fontWeight = FontWeight.Normal,
            color = NeutralMetal,
            modifier = Modifier
                .align(
                    Alignment.BottomStart
                )
                .padding(bottom = 24.dp, start = 24.dp)
        )
        Text(
            mobile.ifEmpty { "휴대폰 번호" },
            fontSize = 10.sp,
            fontFamily = fonts,
            fontWeight = FontWeight.Normal,
            color = NeutralMetal,
            modifier = Modifier
                .align(
                    Alignment.BottomStart
                )
                .padding(bottom = 38.dp, start = 24.dp)
        )
        Text(
            address.ifEmpty { "주소" },
            fontSize = 10.sp,
            fontFamily = fonts,
            fontWeight = FontWeight.Normal,
            color = NeutralMetal,
            modifier = Modifier
                .align(
                    Alignment.BottomStart
                )
                .padding(bottom = 52.dp, start = 24.dp)
        )
        Text(
            if (team.isNotEmpty() && role.isNotEmpty()) "$team - $role" else "부서 - 직책",
            fontSize = 12.sp,
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            color = NeutralDarkGray,
            modifier = Modifier
                .align(
                    Alignment.BottomStart
                )
                .padding(bottom = 68.dp, start = 24.dp)
        )
        Text(
            company.ifEmpty { "회사명" },
            fontSize = 12.sp,
            fontFamily = fonts,
            fontWeight = FontWeight.SemiBold,
            color = NeutralDarkGray,
            modifier = Modifier
                .align(
                    Alignment.BottomStart
                )
                .padding(bottom = 84.dp, start = 24.dp)
        )
        Text(
            logo.ifEmpty { "로고 이미지" },
            fontSize = 12.sp,
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.End,
            color = NeutralMetal,
            modifier = Modifier
                .align(
                    Alignment.TopEnd
                )
                .padding(top = 24.dp, end = 24.dp)
        )
        Text(
            "QR 코드",
            fontSize = 12.sp,
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.End,
            color = NeutralMetal,
            modifier = Modifier
                .align(
                    Alignment.BottomEnd
                )
                .padding(bottom = 24.dp, end = 24.dp)
        )
    }
}