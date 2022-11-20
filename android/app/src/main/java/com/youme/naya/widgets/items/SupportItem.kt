package com.youme.naya.widgets.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.youme.naya.widgets.home.ViewCard

private val CardModifier = Modifier
    .width(200.dp)
    .height(360.dp)
    .shadow(elevation = 6.dp)

@Composable
fun SupportCard(supportCard: ViewCard) {
    Card(CardModifier) {
        Image(rememberAsyncImagePainter(supportCard.uri), null, Modifier.fillMaxSize())
    }
}