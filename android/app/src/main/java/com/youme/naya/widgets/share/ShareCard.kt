package com.youme.naya.widgets.share

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.youme.naya.R
import com.youme.naya.ui.theme.PrimaryGradientBrushH

@Composable
fun ShareCard() {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Box(
            Modifier
                .width(155.dp)
                .height(280.dp)
                .shadow(4.dp)
                .background(PrimaryGradientBrushH), Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.home_logo_text),
                null,
                Modifier.rotate(270f),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}