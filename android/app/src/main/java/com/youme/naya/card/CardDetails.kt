package com.youme.naya.card

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.youme.naya.components.OutlinedSmallButton
import com.youme.naya.components.PrimarySmallButton
import com.youme.naya.database.entity.Card
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.ui.theme.fonts

@Composable
fun CardDetailsMainScreen(cardId: Int) {
    val cardViewModel: CardViewModel = hiltViewModel()
    val context = LocalContext.current
    val navController = NavHostController(context)

    cardViewModel.getCardFromId(cardId)
    val card: Card? = cardViewModel.selectResult.collectAsState().value

    if (card != null) {
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
                listOf("이름", card.name),
                listOf("영어 이름", card.engName),
                listOf("이메일", card.email),
                listOf("휴대폰 번호", card.mobile),
                listOf("주소", card.address),
                listOf("부서", card.team),
                listOf("직책", card.role),
                listOf("회사명", card.company),
            )

            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(cardData) { index, item ->
                    CardDetailsItem(item[0], item[1])
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PrimarySmallButton(text = "수정하기") {

                }
                OutlinedSmallButton(text = "삭제하기") {

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
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = fieldName, fontFamily = fonts, fontWeight = FontWeight.SemiBold)
        Text(text = fieldValue, fontFamily = fonts, textAlign = TextAlign.End)
    }
}

@Preview
@Composable
fun CardDetailsMainScreenPreview() {
    CardDetailsMainScreen(1)
}