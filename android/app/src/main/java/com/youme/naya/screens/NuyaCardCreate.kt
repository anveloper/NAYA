package com.youme.naya.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
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
fun NuyaCardCreateScreen(navController: NavHostController, kind: Int = 0) {
    val cardViewModel: CardViewModel = hiltViewModel()
    val ctx = LocalContext.current

    var name by remember { mutableStateOf("") }
    var eng_name by remember { mutableStateOf("") }
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
    var memo_content by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BasicTextField(text = name, placeholder = "이름", onChange = { name = it })
        BasicTextField(text = eng_name, placeholder = "영어 이름", onChange = { eng_name = it })
        BasicTextField(text = email, placeholder = "이메일", onChange = { email = it })
        BasicTextField(text = mobile, placeholder = "휴대폰번호", onChange = { mobile = it })
        BasicTextField(text = address, placeholder = "주소", onChange = { address = it })
        BasicTextField(text = company, placeholder = "회사명", onChange = { company = it })
        BasicTextField(text = team, placeholder = "부서", onChange = { team = it })
        BasicTextField(text = role, placeholder = "직책", onChange = { role = it })
        BasicTextField(text = tel, placeholder = "전화번호", onChange = { tel = it })
        BasicTextField(text = fax, placeholder = "팩스번호", onChange = { fax = it })
//            BasicTextField(text = background, placeholder = "뒷면 배경", onChange = { background = it })
//            BasicTextField(text = logo, placeholder = "로고", onChange = { logo = it })
//            BasicTextField(text = memo1, placeholder = "메모1", onChange = { memo1 = it })
//            BasicTextField(text = memo2, placeholder = "메모2", onChange = { memo2 = it })
//            BasicTextField(text = memo3, placeholder = "메모3", onChange = { memo3 = it })
//            BasicTextField(text = memo_content, placeholder = "메모", onChange = { memo_content = it })
        PrimaryBigButton(text = "저장") {
            if (name.isNotEmpty() && mobile.isNotEmpty()) {
                val card = Card(
                    0,
                    name,
                    eng_name,
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
                    memo_content
                )

                cardViewModel.addCard(card)
                Toast.makeText(ctx, "카드 생성이 완료되었어요", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }
    }
}

//@Composable
//@Preview
//fun NuyaCardCreateScreenPreview() {
//    NuyaCardCreateScreen()
//}