package com.youme.naya.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youme.naya.widgets.common.HeaderBar
import com.youme.naya.widgets.common.NayaBcardSwitchButtons
import com.youme.naya.widgets.home.MyBCardList
import com.youme.naya.widgets.home.MyNayaCardList

private val HomeModifier = Modifier
    .fillMaxSize()
    .background(Color.White)
    .padding(bottom = 80.dp) // main 전체에 한번에 주는게 맞는 듯합니다.

@Composable
fun HomeScreen() {
    var (currentCardId, setCurrentCardId) = rememberSaveable {
        mutableStateOf(1)
    }

    Column(HomeModifier) {
        HeaderBar()
        NayaBcardSwitchButtons(
            nayaTab = { MyNayaCardList() },
            bCardTab = { MyBCardList() }
        )
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}