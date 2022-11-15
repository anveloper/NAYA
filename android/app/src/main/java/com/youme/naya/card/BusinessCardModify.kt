package com.youme.naya.card

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.youme.naya.components.BasicTextField
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.database.entity.Card
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.databinding.BusinessCardBinding
import com.youme.naya.utils.convertView2Bitmap
import com.youme.naya.utils.saveCardImage

@Composable
fun BusinessCardModifyScreen(navController: NavHostController, card: Card) {
    val cardViewModel: CardViewModel = hiltViewModel()
    val ctx = LocalContext.current

    var name by remember { mutableStateOf(card.name ?: "") }
    var engName by remember { mutableStateOf(card.engName ?: "") }
    var email by remember { mutableStateOf(card.email ?: "") }
    var mobile by remember { mutableStateOf(card.mobile ?: "") }
    var address by remember { mutableStateOf(card.address ?: "") }
    var company by remember { mutableStateOf(card.company ?: "") }
    var team by remember { mutableStateOf(card.team ?: "") }
    var role by remember { mutableStateOf(card.role ?: "") }
//    var fax by remember { mutableStateOf(card.fax ?: "") }
    var tel by remember { mutableStateOf(card.tel ?: "") }
//    var background by remember { mutableStateOf(card.background ?: "") }
//    var logo by remember { mutableStateOf(card.logo ?: "") }
//    var memo1 by remember { mutableStateOf(card.memo1 ?: "") }
//    var memo2 by remember { mutableStateOf(card.memo2 ?: "") }
//    var memo3 by remember { mutableStateOf(card.memo3 ?: "") }
    var memoContent by remember { mutableStateOf(card.memoContent ?: "") }

    var templateId by remember { mutableStateOf(card.templateId) }
    var cardImage: Bitmap? by remember { mutableStateOf(null) }
    var bCardBinding: BusinessCardBinding? = null

    val isModified =
        name != (card.name ?: "") || engName != (card.engName ?: "") || email != (card.email
            ?: "") || mobile != (card.mobile ?: "") || address != (card.address
            ?: "") || company != (card.company ?: "") || team != (card.team
            ?: "") || role != (card.role ?: "") || tel != (card.tel
            ?: "") || memoContent != (card.memoContent ?: "")
//            fax != card.fax || background != card.background || logo != card.logo ||
//            memo1 != card.memo1 || memo2 != card.memo2 || memo3 != card.memo3 ||

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
//        BusinessCardTemplate(
//            name, engName, email, mobile, address, team, role, company, null
//        )

        if (templateId == -1) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .aspectRatio(9 / 5f),
                shape = RectangleShape,
                elevation = 4.dp
            ) {
                Image(
                    painter = rememberImagePainter(BitmapFactory.decodeFile(card.path)),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        } else {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .aspectRatio(9f / 5f)
            ) {
                AndroidViewBinding(
                    { inflater, parent, _ ->
                        BusinessCardBinding.inflate(inflater)
                    },
                    Modifier.fillMaxSize()
                ) {
                    bCardBinding = this

                    val teamAndRole = mutableListOf<String>()
                    teamAndRole.add(team.ifBlank { "부서" })
                    teamAndRole.add(role.ifBlank { "직책" })

                    this.bcardName.text = name.ifBlank { "이름" }
                    this.bcardEnglishName.text = engName.ifBlank { "영어 이름" }
                    this.bcardCompany.text = company.ifBlank { "회사명" }
                    this.bcardTeamAndRole.text = teamAndRole.joinToString(" | ")
                    this.bcardAddress.text = address.ifBlank { "주소" }
                    this.bcardMobile.text = mobile.ifBlank { "휴대폰 번호" }
                    this.bcardEmail.text = email.ifBlank { "이메일" }
                    this.bcardLogo.text = "로고 이미지"
                    this.bcardQrcode.text = "QR코드 이미지"

                    this.businessCard.minWidth = 990
                    this.businessCard.minHeight = 550

                    cardImage = convertView2Bitmap(this.root)
                }
            }
        }


        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                BasicTextField(text = name, placeholder = "이름", onChange = { name = it })
            }
            item {
                BasicTextField(text = engName, placeholder = "영어 이름", onChange = { engName = it })
            }
            item {
                BasicTextField(text = email, placeholder = "이메일", onChange = { email = it })
            }
            item {
                BasicTextField(text = mobile, placeholder = "휴대폰번호", onChange = { mobile = it })
            }
            item {
                BasicTextField(text = address, placeholder = "주소", onChange = { address = it })
            }
            item {
                BasicTextField(text = company, placeholder = "회사명", onChange = { company = it })
            }
            item {
                BasicTextField(text = team, placeholder = "부서", onChange = { team = it })
            }
            item {
                BasicTextField(text = role, placeholder = "직책", onChange = { role = it })
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
                    val isValid = name.isNotBlank() || engName.isNotBlank() || email.isNotBlank()
                            || mobile.isNotBlank() || address.isNotBlank() || company.isNotBlank()
                            || team.isNotBlank() || role.isNotBlank()
                            || tel.isNotBlank() || memoContent.isNotBlank()

                    if (isValid) {
                        var newPath: String? = null
                        if (templateId == -1) {
                            newPath = card.path
                        } else {
                            if (name.isBlank()) bCardBinding?.bcardName?.text = ""
                            if (engName.isBlank()) bCardBinding?.bcardEnglishName?.text = ""
                            if (email.isBlank()) bCardBinding?.bcardEmail?.text = ""
                            if (mobile.isBlank()) bCardBinding?.bcardMobile?.text = ""
                            if (address.isBlank()) bCardBinding?.bcardAddress?.text = ""
                            if (company.isBlank()) bCardBinding?.bcardCompany?.text = ""
                            if (team.isBlank() && role.isBlank()) bCardBinding?.bcardTeamAndRole?.text = ""
                            else if (role.isBlank()) bCardBinding?.bcardTeamAndRole?.text = team
                            else if (team.isBlank()) bCardBinding?.bcardTeamAndRole?.text = role
                            bCardBinding?.bcardLogo?.text = ""
                            bCardBinding?.bcardQrcode?.text = ""

                            cardImage = bCardBinding?.root?.let { convertView2Bitmap(it) }
                            newPath = saveCardImage(ctx, cardImage!!, true)
                        }

                        val newCard = Card(
                            NayaCardId = card.NayaCardId,
                            name = name.ifBlank { null },
                            engName = engName.ifBlank { null },
                            kind = card.kind,
                            email = email.ifBlank { null },
                            mobile = mobile.ifBlank { null },
                            address = address.ifBlank { null },
                            company = company.ifBlank { null },
                            team = team.ifBlank { null },
                            role = role.ifBlank { null },
                            tel = tel.ifBlank { null },
                            memoContent = memoContent.ifBlank { null },
//                            background,
//                            logo,
//                            fax,
//                            memo1,
//                            memo2,
//                            memo3,
                            path = newPath,
                            templateId = templateId
                        )

                        cardViewModel.updateCard(newCard)
                        Toast.makeText(ctx, "카드 수정이 완료되었어요", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(ctx, "필수 입력 양식을 채워주세요", Toast.LENGTH_SHORT).show()
                    }
                }
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                )
            }
        }
    }
}
