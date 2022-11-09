package com.youme.naya.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.youme.naya.card.BusinessCardTemplate
import com.youme.naya.components.BasicTextField
import com.youme.naya.components.OutlinedSmallButton
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.database.entity.Card
import com.youme.naya.database.viewModel.CardViewModel


@Composable
fun BCardCreateByCameraScreen(navController: NavHostController, result: String) {
    val cardViewModel: CardViewModel = hiltViewModel()
    val ctx = LocalContext.current
    val stringValues = Uri.decode(result).lines().iterator()
    var idx by remember { mutableStateOf(0) }

    val fieldsNameList = listOf(
        "name", "engName", "email", "mobile", "address", "company", "team", "role",
        "fax", "background", "logo", "memo1", "memo2", "memo3", "memoContent"
    )
    val fieldsLabelList = listOf(
        "이름", "영어 이름", "이메일", "휴대폰 번호", "주소", "회사명", "부서", "직책",
        "팩스 번호", "배경 이미지", "로고 이미지", "기타 1", "기타 2", "기타 3", "메모"
    )

    val fieldsValueList = List(fieldsNameList.size) { _ -> "" }
    val fieldsValueState = remember { fieldsValueList.toMutableStateList() }

    val fieldsRequiredList = listOf(
        true, true, true, true, true, true, true, true,
        false, false, false, false, false, false, false
    )
    val fieldsRequiredState = remember { fieldsRequiredList.toMutableStateList() }

    val fieldSelectedList = List(fieldsNameList.size) { _ -> false }
    val fieldSelectedState = remember { fieldSelectedList.toMutableStateList() }

    val dropdownMenuList = List(fieldsNameList.size) { _ -> false }
    val dropdownMenuState = remember { dropdownMenuList.toMutableStateList() }

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
            items(fieldsNameList.size) { index ->
                FieldItem(name, "이름 *", 0, dropdownMenuState, fieldsLabelList) { name = it }
            }
            item {
                FieldItem(name, "이름 *", 0, dropdownMenuState, fieldsLabelList) { name = it }
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
                            1,
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
                        .height(40.dp)
                )
            }
        }
    }
}

@Composable
fun FieldItem(
    text: String,
    placeholder: String,
    idx: Int,
    dropdownMenuState: SnapshotStateList<Boolean>,
    fieldsLabelList: List<String>,
    onChange: (String) -> Unit
) {
    Row {
        OutlinedSmallButton(text = "Dropdown Menu") {
            dropdownMenuState[idx] = true
        }
        DropdownMenu(
            expanded = dropdownMenuState[idx],
            onDismissRequest = { dropdownMenuState[idx] = false },
        ) {
            dropdownMenuState.forEachIndexed { index, state ->
                if (!state) {
                    DropdownMenuItem(onClick = {
                        dropdownMenuState[index] = false
                    }) {
                        Text(text = fieldsLabelList[index])
                    }
                }
            }
        }
        BasicTextField(text = text, placeholder = placeholder, onChange = onChange)
    }
}