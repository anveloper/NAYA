package com.youme.naya.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.youme.naya.card.BusinessCardGridList
import com.youme.naya.card.NayaCardGridList
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.widgets.common.NayaBcardSwitchButtons

@Composable
fun NayaCardScreen() {
    val cardViewModel: CardViewModel = hiltViewModel()
    val context = LocalContext.current
    val navController = rememberNavController()

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            NayaBcardSwitchButtons(
                nayaTab = {
                    NayaCardGridList(context, navController)
                },
                bCardTab = {
//                    BusinessCardStackList(context, cardViewModel)
                    BusinessCardGridList(context, navController, cardViewModel)
                }
            )
        }
    }
}

@Preview
@Composable
fun NayaCardScreenPreview() {
    NayaCardScreen()
}
