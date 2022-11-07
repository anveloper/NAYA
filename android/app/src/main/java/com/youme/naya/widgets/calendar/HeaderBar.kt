package com.youme.naya.widgets.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.youme.naya.R

private val CalendarHeaderBtnGroupModifier = Modifier
    .fillMaxWidth()
    .height(64.dp)
    .padding(start = 8.dp, end = 8.dp)

@Composable
fun SearchHeaderBar() {
    Row(
        CalendarHeaderBtnGroupModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Image(
                painter = painterResource(R.drawable.home_icon_search),
                contentDescription = "home alarm button"
            )
        }
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