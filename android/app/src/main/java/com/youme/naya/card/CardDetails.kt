package com.youme.naya.card

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.youme.naya.CardDetailsScreen
import com.youme.naya.components.OutlinedSmallButton
import com.youme.naya.components.PrimarySmallButton
import com.youme.naya.database.entity.Card
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.ui.theme.fonts

@Composable
fun CardDetailsMainScreen(navController: NavHostController, cardId: Int) {
    val cardViewModel: CardViewModel = hiltViewModel()
    val context = LocalContext.current as Activity
    val intent = Intent(context, CardDetailsActivity::class.java)

    cardViewModel.getCardFromId(cardId)
    val card: Card? = cardViewModel.selectResult.collectAsState().value

    val (isClickDelete, SetIsClickDelete) = remember { mutableStateOf(false) }

    if (card != null) {
        if (isClickDelete) {
            DeleteAlertDialog(
                onDelete = {
                    SetIsClickDelete(false)
                    intent.putExtra("finish", 0)
                    context.setResult(ComponentActivity.RESULT_OK, intent)
                    context.finish()
                    cardViewModel.removeCard(card)
                    Toast.makeText(context, "????????? ???????????????", Toast.LENGTH_SHORT).show()
                },
                onCancel = { SetIsClickDelete(false) }
            )
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BusinessCardTemplate(
                name = card.name,
                engName = card.engName,
                email = card.email,
                mobile = card.mobile,
                address = card.address,
                team = card.team,
                role = card.role,
                company = card.company,
                logo = card.logo
            )

            val cardData = listOf(
                listOf("??????", card.name),
                listOf("?????? ??????", card.engName),
                listOf("?????????", card.email),
                listOf("????????? ??????", card.mobile),
                listOf("??????", card.address),
                listOf("??????", card.team),
                listOf("??????", card.role),
                listOf("?????????", card.company),
                listOf("??????", card.memoContent),
            )

            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                itemsIndexed(cardData) { index, item ->
                    if (!item[1].isNullOrBlank()) {
                        CardDetailsItem(item[0]!!, item[1]!!)
                    }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PrimarySmallButton(text = "????????????") {
                    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter = moshi.adapter(Card::class.java).lenient()
                    val cardJson = jsonAdapter.toJson(card)

                    navController.navigate(
                        CardDetailsScreen.BCardModify.route.replace(
                            "{card}",
                            cardJson
                        )
                    )
                }
                OutlinedSmallButton(text = "????????????") {
                    SetIsClickDelete(true)
                }
            }
        }
    }
}

@Composable
fun CardDetailsItem(
    fieldName: String,
    fieldValue: String
) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = fieldName, fontFamily = fonts, fontWeight = FontWeight.Bold)
        Text(text = fieldValue, fontFamily = fonts, textAlign = TextAlign.End)
    }
}
