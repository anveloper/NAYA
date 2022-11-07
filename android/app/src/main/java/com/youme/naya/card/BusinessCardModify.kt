package com.youme.naya.card

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.youme.naya.components.BasicTextField
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.database.entity.Card
import com.youme.naya.database.viewModel.CardViewModel

@Composable
fun BusinessCardModifyScreen(navController: NavHostController, card: Card) {
    val cardViewModel: CardViewModel = hiltViewModel()
    val ctx = LocalContext.current

    var name by remember { mutableStateOf(card.name) }
    var engName by remember { mutableStateOf(card.engName) }
    var email by remember { mutableStateOf(card.email) }
    var mobile by remember { mutableStateOf(card.mobile) }
    var address by remember { mutableStateOf(card.address) }
    var company by remember { mutableStateOf(card.company) }
    var team by remember { mutableStateOf(card.team) }
    var role by remember { mutableStateOf(card.role) }
    var fax by remember { mutableStateOf(card.fax) }
    var tel by remember { mutableStateOf(card.tel) }
    var background by remember { mutableStateOf(card.background) }
    var logo by remember { mutableStateOf(card.logo) }
    var memo1 by remember { mutableStateOf(card.memo1) }
    var memo2 by remember { mutableStateOf(card.memo2) }
    var memo3 by remember { mutableStateOf(card.memo3) }
    var memoContent by remember { mutableStateOf(card.memo_content) }

    val isModified = name != card.name || engName != card.engName || email != card.email ||
            mobile != card.mobile || address != card.address || company != card.company ||
            team != card.team || role != card.role || fax != card.fax ||
            tel != card.tel || background != card.background || logo != card.logo ||
            memo1 != card.memo1 || memo2 != card.memo2 || memo3 != card.memo3 ||
            memoContent != card.memo_content

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        BusinessCardTemplate(
            name, engName, email, mobile, address, team, role, company, logo
        )

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                BasicTextField(text = name, placeholder = "이름 *", onChange = { name = it })
            }
            item {
                BasicTextField(text = engName, placeholder = "영어 이름 *", onChange = { engName = it })
            }
            item {
                BasicTextField(text = email, placeholder = "이메일 *", onChange = { email = it })
            }
            item {
                BasicTextField(text = mobile, placeholder = "휴대폰번호 *", onChange = { mobile = it })
            }
            item {
                BasicTextField(text = address, placeholder = "주소 *", onChange = { address = it })
            }
            item {
                BasicTextField(text = company, placeholder = "회사명 *", onChange = { company = it })
            }
            item {
                BasicTextField(text = team, placeholder = "부서 *", onChange = { team = it })
            }
            item {
                BasicTextField(text = role, placeholder = "직책 *", onChange = { role = it })
            }
            item {
                BasicTextField(text = tel, placeholder = "전화번호", onChange = { tel = it })
            }
//            BasicTextField(text = fax, placeholder = "팩스번호", onChange = { fax = it })
//            BasicTextField(text = background, placeholder = "뒷면 배경", onChange = { background = it })
//            BasicTextField(text = logo, placeholder = "로고", onChange = { logo = it })
//            BasicTextField(text = memo1, placeholder = "메모1", onChange = { memo1 = it })
//            BasicTextField(text = memo2, placeholder = "메모2", onChange = { memo2 = it })
//            BasicTextField(text = memo3, placeholder = "메모3", onChange = { memo3 = it })
            item {
                BasicTextField(
                    text = memoContent,
                    placeholder = "메모",
                    onChange = { memoContent = it })
            }
            item {
                PrimaryBigButton(text = "수정", enabled = isModified) {
                    val isValid = name.isNotEmpty() && engName.isNotEmpty() && email.isNotEmpty()
                            && mobile.isNotEmpty() && address.isNotEmpty() && company.isNotEmpty()
                            && team.isNotEmpty() && role.isNotEmpty()

                    if (isValid) {
                        val newCard = Card(
                            card.NayaCardId,
                            name,
                            engName,
                            kind = 1,
                            email,
                            mobile,
                            address,
                            company,
                            team,
                            role,
                            background,
                            logo,
                            fax,
                            tel,
                            memo1,
                            memo2,
                            memo3,
                            memoContent
                        )

                        cardViewModel.updateCard(newCard)
                        Toast.makeText(ctx, "카드 수정이 완료되었어요", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(ctx, "필수 입력 양식을 채워주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
