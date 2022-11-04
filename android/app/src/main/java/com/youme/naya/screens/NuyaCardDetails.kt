package com.youme.naya.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.youme.naya.card.BusinessCardTemplate
import com.youme.naya.database.entity.Card
import com.youme.naya.database.viewModel.CardViewModel

@Composable
fun NuyaCardDetailsScreen(navController: NavHostController, id: Int?) {
    val cardViewModel: CardViewModel = hiltViewModel()
    val context = LocalContext.current
    lateinit var card: Card

    if (id == null) {
        Toast.makeText(context, "올바른 카드가 아니에요", Toast.LENGTH_SHORT).show()
        navController.popBackStack()
    } else {
        cardViewModel.getCardFromId(id)
        card = cardViewModel.selectResult.collectAsState().value!!
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BusinessCardTemplate(
            name = card.name,
            engName = card.engName,
            email = card.email,
            mobile = card.email,
            address = card.address,
            team = card.team,
            role = card.role,
            company = card.company,
            logo = card.logo
        )

        LazyColumn(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(text = card.toString())
            }
        }
    }
}

//@Composable
//@Preview
//fun NuyaCardDetailsScreenPreview() {
//    NuyaCardDetailsScreen()
//}