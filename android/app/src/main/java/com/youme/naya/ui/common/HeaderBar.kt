package com.youme.naya.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.youme.naya.R

private val HomeTitleModifier = Modifier
    .fillMaxWidth()
    .height(60.dp)
    .background(Color.White)
    .padding(20.dp)

private val HomeLogoModifier = Modifier
    .height(24.dp)

private val HomeSettingBtnGroupModifier = Modifier
    .height(24.dp)

@Composable
fun Headerbar() {
    Row(
        HomeTitleModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            HomeLogoModifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.home_logo_image),
                contentDescription = "logo",
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 4.dp)
            )
            Image(
                painter = painterResource(R.drawable.home_logo_text),
                contentDescription = "logo text",
                modifier = Modifier.fillMaxHeight()
            )
        }
        Row(
            HomeSettingBtnGroupModifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    painter = painterResource(R.drawable.home_icon_alarm),
                    contentDescription = "home alarm button"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    painter = painterResource(R.drawable.home_icon_setting),
                    contentDescription = "home alarm button"
                )
            }
        }
    }
}