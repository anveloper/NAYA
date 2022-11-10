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


fun isValid(fields: SnapshotStateList<String>, fieldsRequiredList: List<Boolean>): Boolean {
    fields.forEachIndexed { index, field ->
        if (fieldsRequiredList[index] && field.isBlank()) return false
    }
    return true
}

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

    val fieldSelectedList = List(fieldsNameList.size) { _ -> false }
    val fieldSelectedState = remember { fieldSelectedList.toMutableStateList() }

    val dropdownMenuList = List(fieldsNameList.size) { _ -> false }
    val dropdownMenuState = remember { dropdownMenuList.toMutableStateList() }

//    var name by remember { mutableStateOf("") }
//    var engName by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var mobile by remember { mutableStateOf("") }
//    var address by remember { mutableStateOf("") }
//    var company by remember { mutableStateOf("") }
//    var team by remember { mutableStateOf("") }
//    var role by remember { mutableStateOf("") }
//    var fax by remember { mutableStateOf("") }
//    var tel by remember { mutableStateOf("") }
//    var background by remember { mutableStateOf("") }
//    var logo by remember { mutableStateOf("") }
//    var memo1 by remember { mutableStateOf("") }
//    var memo2 by remember { mutableStateOf("") }
//    var memo3 by remember { mutableStateOf("") }
//    var memoContent by remember { mutableStateOf("") }

    var inputValueList = List(fieldsNameList.size) { _ -> ""}
    var inputValueState = remember { inputValueList.toMutableStateList() }

    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BusinessCardTemplate(
            name = inputValueState[0],
            engName = inputValueState[1],
            email = inputValueState[2],
            mobile = inputValueState[3],
            address = inputValueState[4],
            company = inputValueState[5],
            team = inputValueState[6],
            role = inputValueState[7],
            logo = inputValueState[11]
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
                PrimaryBigButton(text = "저장") {
                    if (isValid(fieldsValueState, fieldsRequiredList)) {
                        val card = Card(
                            0,
                            name = inputValueState[0],
                            engName = inputValueState[1],
                            1,
                            email = inputValueState[2],
                            mobile = inputValueState[3],
                            address = inputValueState[4],
                            company = inputValueState[5],
                            team = inputValueState[6],
                            role = inputValueState[7],
                            fax = inputValueState[8],
                            tel = inputValueState[9],
                            background = inputValueState[10],
                            logo = inputValueState[11],
                            memo1 = inputValueState[12],
                            memo2 = inputValueState[13],
                            memo3 = inputValueState[14],
                            memoContent = inputValueState[15]
                        )

                        cardViewModel.addCard(card)
                        Toast.makeText(ctx, "카드 생성이 완료되었어요", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(ctx, "필수 입력 양식을 채워주세요", Toast.LENGTH_SHORT).show()
                    }
                }
                Spacer(Modifier.fillMaxWidth().height(40.dp))
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