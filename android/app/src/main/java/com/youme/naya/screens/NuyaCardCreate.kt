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
import com.youme.naya.components.BasicTextField
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.database.entity.Card
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.ui.theme.*


@Composable
fun NuyaCardCreateScreen(navController: NavHostController, kind: Int = 1) {
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
            .padding(bottom = 96.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .aspectRatio(9f / 5f)
                .background(NeutralWhite)
                .shadow(2.dp)
                .drawBehind {
                    val strokeWidth = 16f
                    val y = size.height - strokeWidth / 2

                    drawLine(
                        PrimaryBlue,
                        Offset(0f, y),
                        Offset(size.width + 32, y),
                        strokeWidth
                    )
                }) {
            Text(
                name.ifEmpty { "이름" },
                fontSize = 16.sp,
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                color = NeutralDarkGray,
                modifier = Modifier
                    .align(
                        Alignment.TopStart
                    )
                    .padding(top = 16.dp, start = 16.dp)
            )
            Text(
                engName.ifEmpty { "영어 이름" },
                fontSize = 11.sp,
                fontFamily = fonts,
                fontWeight = FontWeight.Medium,
                color = NeutralMetal,
                modifier = Modifier
                    .align(
                        Alignment.TopStart
                    )
                    .padding(top = 38.dp, start = 16.dp)
            )
            Text(
                email.ifEmpty { "이메일" },
                fontSize = 10.sp,
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                color = NeutralMetal,
                modifier = Modifier
                    .align(
                        Alignment.BottomStart
                    )
                    .padding(bottom = 16.dp, start = 16.dp)
            )
            Text(
                mobile.ifEmpty { "휴대폰 번호" },
                fontSize = 10.sp,
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                color = NeutralMetal,
                modifier = Modifier
                    .align(
                        Alignment.BottomStart
                    )
                    .padding(bottom = 28.dp, start = 16.dp)
            )
            Text(
                address.ifEmpty { "주소" },
                fontSize = 10.sp,
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                color = NeutralMetal,
                modifier = Modifier
                    .align(
                        Alignment.BottomStart
                    )
                    .padding(bottom = 40.dp, start = 16.dp)
            )
            Text(
                if (team.isNotEmpty() && role.isNotEmpty()) "$team - $role" else "부서 - 직책",
                fontSize = 12.sp,
                fontFamily = fonts,
                fontWeight = FontWeight.Medium,
                color = NeutralDarkGray,
                modifier = Modifier
                    .align(
                        Alignment.BottomStart
                    )
                    .padding(bottom = 54.dp, start = 16.dp)
            )
            Text(
                company.ifEmpty { "회사명" },
                fontSize = 12.sp,
                fontFamily = fonts,
                fontWeight = FontWeight.SemiBold,
                color = NeutralDarkGray,
                modifier = Modifier
                    .align(
                        Alignment.BottomStart
                    )
                    .padding(bottom = 68.dp, start = 16.dp)
            )
            Text(
                "로고 이미지",
                fontSize = 12.sp,
                fontFamily = fonts,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End,
                color = NeutralMetal,
                modifier = Modifier
                    .align(
                        Alignment.TopEnd
                    )
                    .padding(top = 16.dp, end = 16.dp)
            )
            Text(
                "QR 코드",
                fontSize = 12.sp,
                fontFamily = fonts,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End,
                color = NeutralMetal,
                modifier = Modifier
                    .align(
                        Alignment.BottomEnd
                    )
                    .padding(bottom = 16.dp, end = 16.dp)
            )
        }

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
            }
        }
    }
}

//@Composable
//@Preview
//fun NuyaCardCreateScreenPreview() {
//    NuyaCardCreateScreen(rememberNavController(), 1)
//}