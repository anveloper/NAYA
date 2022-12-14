package com.youme.naya.screens

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.youme.naya.card.CardFace
import com.youme.naya.card.FlippableCard
import com.youme.naya.card.RotationAxis
import com.youme.naya.components.BasicTextField
import com.youme.naya.components.OutlinedSmallButton
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.database.entity.Card
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.ui.theme.PrimaryBlue
import com.youme.naya.utils.saveCardImage
import com.youme.naya.utils.saveSharedCardImage


//val fieldsNameList = listOf(
//    "name", "engName", "email", "mobile", "address", "company", "team", "role",
//    "fax", "background", "logo", "memo1", "memo2", "memo3", "memoContent"
//)
//val fieldsLabelList = listOf(
//    "이름", "영어 이름", "이메일", "휴대폰 번호", "주소", "회사명", "부서", "직책",
//    "팩스 번호", "배경 이미지", "로고 이미지", "기타 1", "기타 2", "기타 3", "메모"
//)
val fieldsNameList = listOf(
    "name", "engName", "email", "mobile", "address", "company", "team", "role", "memoContent"
)
val fieldsLabelList = listOf(
    "이름", "영어 이름", "이메일", "휴대폰 번호", "주소", "회사명", "부서", "직책", "메모"
)

fun isValid(mappedValueMap: SnapshotStateMap<String, String>): Boolean {
    var result = false
    mappedValueMap.values.forEach { value ->
        if (value.isNotBlank()) result = true
    }
    return result
}

/**
 * 문자열을 개행 문자 기준으로 각 라인으로 나누고, 빈 라인을 제거 후 리스트 형식으로 반환하는 함수
 */
fun removeBlankLines(ocrResult: String): MutableList<String> {
    val result = mutableListOf<String>()
    ocrResult.lines().forEach { line ->
        if (line.isNotBlank()) result.add(line)
    }
    return result
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BCardCreateByCameraScreen(
    navController: NavHostController,
    result: String,
    path: String,
    path2: String,
    isNuya: Boolean,
    isSameImage: Boolean,
) {
    val cardViewModel: CardViewModel = hiltViewModel()
    val ctx = LocalContext.current
    val resultLines = removeBlankLines(Uri.decode(result))

    var flipState by remember { mutableStateOf(CardFace.Front) }

    // 필드 입력 값 리스트 (입력 창 생성순)
    val fieldsValueState = remember(resultLines) {
        mutableStateListOf(*resultLines.map { line -> line }.toTypedArray())
    }

    // 각 드롭다운 메뉴의 오픈 상태 리스트 (입력 창 생성순)
    val dropdownMenuState = remember(resultLines) {
        if (resultLines.isNullOrEmpty()) {
            mutableStateListOf(*(1..5).map { _ -> false }.toTypedArray())
        } else {
            mutableStateListOf(*resultLines.map { _ -> false }.toTypedArray())
        }
    }

    // 각 입력 창과 매핑되는 필드 이름 맵
    val mappedFieldNameMap = remember { mutableStateMapOf<Int, String?>() }

    // 각 입력 창과 매핑되는 필드 레이블 맵
    val mappedFieldLabelMap = remember { mutableStateMapOf<Int, String?>() }

    // 필드 선택 여부 맵
    val fieldSelectedMap = remember(fieldsNameList) {
        mutableStateMapOf(*fieldsNameList.map { field -> field to false }.toTypedArray())
    }

    // fieldsNameList의 각 입력 값에 해당하는 필드의 실제 값
    val mappedValueMap = remember(fieldsNameList) {
        mutableStateMapOf(*fieldsNameList.map { field -> field to "" }.toTypedArray())
    }

    // 추출된 명함 이미지 비트맵
    val cardImageBitmap = BitmapFactory.decodeFile(path)

    // 뒷면이 있는 경우의 뒷면 이미지 비트맵
    val backgroundImageBitmap = if (!isSameImage) {
        BitmapFactory.decodeFile(path2)
    } else null

    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FlippableCard(
            cardFace = flipState,
            onClick = { flipState = it.next },
            axis = RotationAxis.AxisY,
            isHorizontalCard = true,
            modifier = Modifier.padding(bottom = 16.dp),
            back = if (!isSameImage) ({
                Image(
                    painter = rememberAsyncImagePainter(backgroundImageBitmap),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }) else null
        ) {
            Image(
                painter = rememberAsyncImagePainter(cardImageBitmap),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        LazyColumn(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(resultLines) { idx, _ ->
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedSmallButton(text = mappedFieldLabelMap[idx] ?: "선택") {
                        dropdownMenuState[idx] = true
                    }
                    DropdownMenu(
                        expanded = dropdownMenuState[idx],
                        onDismissRequest = { dropdownMenuState[idx] = false },
                    ) {
                        fieldsNameList.forEachIndexed { fieldIdx, fieldName ->
                            if (!fieldSelectedMap[fieldName]!!) {
                                DropdownMenuItem(onClick = {
                                    dropdownMenuState[idx] = false
                                    fieldSelectedMap[fieldName] = true
                                    mappedFieldNameMap[idx] = fieldName
                                    mappedFieldLabelMap[idx] = fieldsLabelList[fieldIdx]
                                    mappedValueMap[fieldName] = fieldsValueState[idx]
                                }) {
                                    Text(text = fieldsLabelList[fieldIdx])
                                }
                            } else {
                                // 이미 선택된 아이템을 고르는 경우 기존에 선택된 아이템에서 현재 값으로 변경
                                DropdownMenuItem(onClick = {
                                    dropdownMenuState[idx] = false
                                    val prevSelected = mappedFieldNameMap.entries.find { entry ->
                                        entry.value == fieldName
                                    }!!
                                    if (prevSelected.key == idx) {
                                        // 이미 선택된 필드를 한 번 더 선택할 경우 해제
                                        fieldSelectedMap[fieldName] = false
                                        mappedFieldNameMap[idx] = null
                                        mappedFieldLabelMap[idx] = null
                                        mappedValueMap[fieldName] = ""
                                    } else {
                                        if (fieldSelectedMap[fieldName] == false) {
                                            // 선택한 필드가 이전에 선택된 적이 없다면
                                            fieldSelectedMap[fieldName] = true
                                            fieldSelectedMap[prevSelected.value!!] = false
                                            mappedFieldNameMap[prevSelected.key] = null
                                            mappedFieldNameMap[idx] = fieldName
                                            mappedFieldLabelMap[prevSelected.key] = null
                                            mappedFieldLabelMap[idx] = fieldsLabelList[fieldIdx]
                                            mappedValueMap[prevSelected.value!!] = ""
                                            mappedValueMap[fieldName] = fieldsValueState[idx]
                                        } else {
                                            // 이미 해당 필드가 선택된 입력창이 있다면
                                            mappedFieldNameMap[prevSelected.key] =
                                                mappedFieldNameMap[idx]
                                            mappedFieldNameMap[idx] = fieldName
                                            mappedFieldLabelMap[prevSelected.key] =
                                                mappedFieldLabelMap[idx]
                                            mappedFieldLabelMap[idx] = fieldsLabelList[fieldIdx]
                                            mappedValueMap[prevSelected.value!!] =
                                                mappedValueMap[fieldName]!!
                                            mappedValueMap[fieldName] = fieldsValueState[idx]
                                        }
                                    }
                                }) {
                                    Text(text = fieldsLabelList[fieldIdx], color = PrimaryBlue)
                                }
                            }
                        }
                    }
                    BasicTextField(
                        text = fieldsValueState[idx],
                        placeholder = mappedFieldLabelMap[idx] ?: "",
                        onChange = {
                            fieldsValueState[idx] = it
                            if (mappedFieldNameMap[idx] != null) {
                                mappedValueMap[mappedFieldNameMap[idx]!!] = it
                            }
                        })
                }
            }

            item {
                PrimaryBigButton(text = "저장") {
                    if (isValid(mappedValueMap)) {
                        val newPath = if (isNuya) {
                            saveSharedCardImage(ctx, cardImageBitmap, true)
                        } else {
                            saveCardImage(ctx, cardImageBitmap, true)
                        }
                        val backgroundPath = if (!isSameImage && backgroundImageBitmap != null) {
                            if (isNuya) {
                                saveSharedCardImage(ctx, backgroundImageBitmap, true)
                            } else {
                                saveCardImage(ctx, backgroundImageBitmap, true)
                            }
                        } else null

                        val card = Card(
                            0,
                            name = mappedValueMap["name"],
                            engName = mappedValueMap["engName"],
                            1,
                            email = mappedValueMap["email"],
                            mobile = mappedValueMap["mobile"],
                            address = mappedValueMap["address"],
                            company = mappedValueMap["company"],
                            team = mappedValueMap["team"],
                            role = mappedValueMap["role"],
                            tel = mappedValueMap["tel"],
                            memoContent = mappedValueMap["memoContent"],
//                            fax = mappedValueMap["fax"],
                            background = backgroundPath,
//                            logo = mappedValueMap["logo"],
//                            memo1 = mappedValueMap["memo1"],
//                            memo2 = mappedValueMap["memo2"],
//                            memo3 = mappedValueMap["memo3"],
                            path = newPath,
                            templateId = -1,
                            isNuya = if (isNuya) 1 else 0
                        )

                        cardViewModel.addCard(card)
                        Toast.makeText(ctx, "카드 생성이 완료되었어요", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(ctx, "적어도 하나 이상의 값을 입력해주세요", Toast.LENGTH_SHORT).show()
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