package com.youme.naya.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.youme.naya.R
import com.youme.naya.components.PrimarySmallButton
import com.youme.naya.ui.theme.NeutralLight
import com.youme.naya.ui.theme.NeutralWhite
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.Typography

@Composable
fun SettingsScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NeutralWhite),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.icon_service_fix),
                "service",
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "서비스 준비중입니다 :)",
                style = Typography.h4,
                color = PrimaryDark
            )
            Spacer(modifier = Modifier.height(20.dp))
            PrimarySmallButton(text = "홈으로 돌아가기", onClick = {
                navController.navigate("home")
            })
        }
    }
}