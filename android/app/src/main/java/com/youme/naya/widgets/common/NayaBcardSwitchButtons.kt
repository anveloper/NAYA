package com.youme.naya.widgets.common

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.youme.naya.R
import com.youme.naya.constant.CardTabConstant
import com.youme.naya.ui.theme.NeutralLightness


private val TabContainerModifier = Modifier
    .fillMaxSize()

private val cardTabModifier = Modifier
    .fillMaxWidth()
    .padding(vertical = 16.dp)

private val CardListModifier = Modifier
    .fillMaxSize()

@Composable
fun NayaBcardSwitchButtons(
    nayaTab: @Composable (() -> Unit),
    bCardTab: @Composable (() -> Unit),
    videoTab: @Composable (() -> Unit) = {},
    isHome: Boolean = false,
    isNuya: Boolean = false
) {
    val (cardTab, setCardTab) = rememberSaveable {
        mutableStateOf(CardTabConstant.NAYA)
    }

    Column(TabContainerModifier) {
        Row(
            cardTabModifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    setCardTab(CardTabConstant.NAYA)
                    NayaTabStore.setCurrTabState("naya")
                }
            ) {
                if (isNuya) {
                    Image(
                        painter = painterResource(R.drawable.home_tab_nuya),
                        contentDescription = "home nuya tab",
                        alpha = if (cardTab == CardTabConstant.NAYA) 1f else 0.3f
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.home_tab_naya),
                        contentDescription = "home naya tab",
                        alpha = if (cardTab == CardTabConstant.NAYA) 1f else 0.3f
                    )
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .height(24.dp)
                    .width(2.dp)
                    .background(NeutralLightness)
            )
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(onClick = {
                setCardTab(CardTabConstant.BCARD)
                NayaTabStore.setCurrTabState("bCard")
            }) {
                Image(
                    painter = painterResource(R.drawable.home_tab_b),
                    contentDescription = "home business tab",
                    alpha = if (cardTab == CardTabConstant.BCARD) 1f else 0.3f
                )
            }
            if (isHome && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(2.dp)
                        .background(NeutralLightness)
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = {
                    setCardTab(CardTabConstant.VIDEO)
//                NayaTabStore.setCurrTabState("video")
                }) {
                    Image(
                        painter = painterResource(R.drawable.home_tab_video),
                        contentDescription = "home business tab",
                        alpha = if (cardTab == CardTabConstant.VIDEO) 1f else 0.3f
                    )
                }
            }
        }
        Row(CardListModifier) {
            when (cardTab) {
                CardTabConstant.NAYA -> nayaTab()
                CardTabConstant.BCARD -> bCardTab()
                CardTabConstant.VIDEO -> videoTab()
            }
        }
    }
}