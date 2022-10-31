package com.youme.naya.widgets.share

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
import com.youme.naya.ui.theme.NeutralMedium

@Composable
fun ShareInfo(
    id: Int,
    content: String
) {
    Column(
        Modifier
            .fillMaxSize(), Arrangement.SpaceEvenly, Alignment.CenterHorizontally
    ) {
        Column(
            Modifier
                .fillMaxWidth(), Arrangement.Top, Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id), null)
            Text(
                text = content,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = NeutralMedium
            )
        }
        Spacer(Modifier.height(280.dp))
        Spacer(Modifier.height(60.dp))
    }
}