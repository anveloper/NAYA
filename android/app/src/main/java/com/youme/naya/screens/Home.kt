package com.youme.naya.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youme.naya.R
import com.youme.naya.constant.HomeConstant

private val HomeModifier = Modifier
    .fillMaxSize()
    .background(Color.White)
    .padding(bottom = 90.dp) // main 전체에 한번에 주는게 맞는 듯합니다.

private val HomeTitleModifier = Modifier
    .fillMaxWidth()
    .height(60.dp)
    .background(Color.White)
    .padding(20.dp)

private val HomeLogoModifier = Modifier
    .height(24.dp)

private val HomeSettingBtnGroupModifier = Modifier
    .height(24.dp)

private val HomeContentModifier = Modifier
    .fillMaxSize()
    .padding(vertical = 32.dp)

private val HomeTabModifier = Modifier
    .fillMaxWidth()
    .height(32.dp)
    .background(Color.Blue)

private val HomeCardListModifier = Modifier
    .fillMaxSize()
    .background(Color.Gray)

@Composable
fun HomeScreen() {
    var (homeTab, setHomeTab) = rememberSaveable {
        mutableStateOf(HomeConstant.HomeNaya)
    }
    Column(HomeModifier) {
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
        Column(HomeContentModifier) {
            Row(HomeTabModifier, horizontalArrangement = Arrangement.Center) {
                Button(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(R.drawable.home_tab_naya),
                        contentDescription = "home naya tab"
                    )
                }
                Button(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(R.drawable.home_tab_b),
                        contentDescription = "home business tab"
                    )
                }
            }
            LazyRow(HomeCardListModifier) {

            }
        }

    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}