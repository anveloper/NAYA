package com.youme.naya.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import com.youme.naya.widgets.home.MyBCardList
import com.youme.naya.widgets.home.MyNayaCardList

private val HomeModifier = Modifier
    .fillMaxSize()
    .background(Color.White)
    .padding(bottom = 80.dp) // main 전체에 한번에 주는게 맞는 듯합니다.

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
    .padding(top = 24.dp)

private val HomeTabModifier = Modifier
    .fillMaxWidth()
    .height(40.dp)

private val HomeCardListModifier = Modifier
    .fillMaxSize()
    .background(Color.White)

@Composable
fun HomeScreen() {
    var (homeTab, setHomeTab) = rememberSaveable {
        mutableStateOf(HomeConstant.HomeNaya)
    }
    var (currentCardId, setCurrentCardId) = rememberSaveable {
        mutableStateOf(1)
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
            Row(
                HomeTabModifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { setHomeTab(HomeConstant.HomeNaya) }
                ) {
                    Image(
                        painter = painterResource(R.drawable.home_tab_naya),
                        contentDescription = "home naya tab",
                        alpha = if (homeTab == HomeConstant.HomeNaya) 1f else 0.3f
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(2.dp)
                        .background(Color(0xFFF2F5F9))
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = { setHomeTab(HomeConstant.HomeBC) }) {
                    Image(
                        painter = painterResource(R.drawable.home_tab_b),
                        contentDescription = "home business tab",
                        alpha = if (homeTab == HomeConstant.HomeBC) 1f else 0.3f
                    )
                }
            }
            Row(HomeCardListModifier) {
                if (homeTab == HomeConstant.HomeNaya) {
                    MyNayaCardList()
                } else {
                    MyBCardList()
                }
            }
        }

    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}