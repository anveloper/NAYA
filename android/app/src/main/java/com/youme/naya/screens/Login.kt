package com.youme.naya.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.R
import com.youme.naya.ui.theme.NeutralLight
import com.youme.naya.ui.theme.PrimaryGradientBrush
import com.youme.naya.ui.theme.fonts


@Composable
fun LoginScreen(content: () -> Unit) {
    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryGradientBrush)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Greeting(text = "Hi, It’s me.")
            Image(
                painter = painterResource(R.drawable.splash_logo_text_light),
                contentDescription = "logo",
                modifier = Modifier
                    .width(150.dp)
                    .padding(bottom = 16.dp)
            )
            SignInGoogleButton { content() }
        }
    }
}

@Composable
fun Greeting(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.body2,
        color = NeutralLight,
        modifier = Modifier.padding(bottom = 8.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        fontFamily = fonts
    )
}

@Composable
fun SignInGoogleButton(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        // border = BorderStroke(width = 1.dp, color = Color.LightGray),
        color = MaterialTheme.colors.surface, shape = MaterialTheme.shapes.small, elevation = 10.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(
                start = 14.dp, end = 12.dp, top = 11.dp, bottom = 11.dp
            )
        ) {
            Image(
                painterResource(R.drawable.ic_share_sns),
                "Google sign button",
                modifier = Modifier.size(32.dp),
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Google로 시작하기",
                style = MaterialTheme.typography.overline,
                color = Color.Gray,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fonts
            )
        }
    }
}


@Preview(
    name = "Login Preview",
    showSystemUi = true,
    showBackground = true
)
@Composable
fun LoginScreenPreview() {
    LoginScreen {

    }
}