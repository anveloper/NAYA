package com.youme.naya.widgets.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircleWaveComp() {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        CircleWithValue(7.3f, Color(0xFFFCFDFF), 500)
        CircleWithValue(5.2f, Color(0xFFF4F7FE), 400)
        CircleWithValue(3.5f, Color(0xFFE9EFFC), 300)
        CircleWithValue(2.1f, Color(0xFFDFE6FB), 200)
    }
}


@Composable
fun CircleWithValue(
    scale: Float,
    color: Color,
    deley: Int
) {

    Box(
        Modifier
            .scale(scale)
            .width(100.dp)
            .height(100.dp)
            .shadow(2.dp, CircleShape)
            .background(color, CircleShape)
    )
}


@Preview(
    name = "Share Wave",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun WavePreview() {
    CircleWaveComp()
}
