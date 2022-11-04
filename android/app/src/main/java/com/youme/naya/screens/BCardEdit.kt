package com.youme.naya.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.youme.naya.card.BusinessCardTemplate
import com.youme.naya.components.BasicTextField
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.database.entity.Card
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.ui.theme.*


@Composable
fun NuyaCardCreateScreen(navController: NavHostController, id: Int?, kind: Int = 1) {
    val cardViewModel: CardViewModel = hiltViewModel()
    val ctx = LocalContext.current

    var name by remember { mutableStateOf("") }
    var engName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var team by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var fax by remember { mutableStateOf("") }
    var tel by remember { mutableStateOf("") }
    var background by remember { mutableStateOf("") }
    var logo by remember { mutableStateOf("") }
    var memo1 by remember { mutableStateOf("") }
    var memo2 by remember { mutableStateOf("") }
    var memo3 by remember { mutableStateOf("") }
    var memoContent by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BusinessCardTemplate(
            name, engName, email, mobile, address, team, role, company, logo
        )

        LazyColumn(
            Modifier.fillMaxWidth(),
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
                PrimaryBigButton(text = "저장") {
                    val isValid = name.isNotEmpty() && engName.isNotEmpty() && email.isNotEmpty()
                            && mobile.isNotEmpty() && address.isNotEmpty() && company.isNotEmpty()
                            && team.isNotEmpty() && role.isNotEmpty()

                    if (isValid) {
                        val card = Card(
                            0,
                            name,
                            engName,
                            kind,
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

                        cardViewModel.addCard(card)
                        Toast.makeText(ctx, "카드 생성이 완료되었어요", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(ctx, "필수 입력 양식을 채워주세요", Toast.LENGTH_SHORT).show()
                    }
                }
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp))
            }
        }
    }
}

//@Composable
//@Preview
//fun NuyaCardCreateScreenPreview() {
//    NuyaCardCreateScreen(rememberNavController(), 1)
//}