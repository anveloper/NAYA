package com.youme.naya.widgets.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.youme.naya.R

private val CardModifier = Modifier
    .width(200.dp)
    .height(360.dp)
    .shadow(elevation = 6.dp)

@Composable
fun CardItemPlus() {
    Card(CardModifier) {
        IconButton(onClick = { /*TODO*/ }) {
            Image(
                painter = painterResource(R.drawable.card_icon_plus),
                contentDescription = "import naya card",
            )

        }
    }
}