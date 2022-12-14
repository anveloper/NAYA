package com.youme.naya.screens

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.view.drawToBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.youme.naya.card.BusinessCardTemplate
import com.youme.naya.components.BasicTextField
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.database.entity.Card
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.databinding.BusinessCardBinding
import com.youme.naya.utils.convertView2Bitmap
import com.youme.naya.utils.saveCardImage


@Composable
fun BCardCreateScreen(navController: NavHostController, kind: Int = 1) {
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
//    var fax by remember { mutableStateOf("") }
    var tel by remember { mutableStateOf("") }
//    var background by remember { mutableStateOf("") }
//    var logo by remember { mutableStateOf("") }
//    var memo1 by remember { mutableStateOf("") }
//    var memo2 by remember { mutableStateOf("") }
//    var memo3 by remember { mutableStateOf("") }
    var memoContent by remember { mutableStateOf("") }

    var cardImage: Bitmap? by remember { mutableStateOf(null) }
    var bCardBinding: BusinessCardBinding? = null

    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
//        BusinessCardTemplate(
//            name, engName, email, mobile, address, team, role, company, null
//        )
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
                teamAndRole.add(team.ifBlank { "??????" })
                teamAndRole.add(role.ifBlank { "??????" })

                this.bcardName.text = name.ifBlank { "??????" }
                this.bcardEnglishName.text = engName.ifBlank { "?????? ??????" }
                this.bcardCompany.text = company.ifBlank { "?????????" }
                this.bcardTeamAndRole.text = teamAndRole.joinToString(" | ")
                this.bcardAddress.text = address.ifBlank { "??????" }
                this.bcardMobile.text = mobile.ifBlank { "????????? ??????" }
                this.bcardEmail.text = email.ifBlank { "?????????" }
                this.bcardLogo.text = "?????? ?????????"
                this.bcardQrcode.text = "QR?????? ?????????"

                this.businessCard.minWidth = 990
                this.businessCard.minHeight = 550

                cardImage = convertView2Bitmap(this.root)
            }
        }

        LazyColumn(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                BasicTextField(text = name, placeholder = "??????", onChange = { name = it })
            }
            item {
                BasicTextField(text = engName, placeholder = "?????? ??????", onChange = { engName = it })
            }
            item {
                BasicTextField(text = email, placeholder = "?????????", onChange = { email = it })
            }
            item {
                BasicTextField(text = mobile, placeholder = "???????????????", onChange = { mobile = it })
            }
            item {
                BasicTextField(text = address, placeholder = "??????", onChange = { address = it })
            }
            item {
                BasicTextField(text = company, placeholder = "?????????", onChange = { company = it })
            }
            item {
                BasicTextField(text = team, placeholder = "??????", onChange = { team = it })
            }
            item {
                BasicTextField(text = role, placeholder = "??????", onChange = { role = it })
            }
            item {
                BasicTextField(text = tel, placeholder = "????????????", onChange = { tel = it })
            }
//            BasicTextField(text = fax, placeholder = "????????????", onChange = { fax = it })
//            BasicTextField(text = background, placeholder = "?????? ??????", onChange = { background = it })
//            BasicTextField(text = logo, placeholder = "??????", onChange = { logo = it })
//            BasicTextField(text = memo1, placeholder = "??????1", onChange = { memo1 = it })
//            BasicTextField(text = memo2, placeholder = "??????2", onChange = { memo2 = it })
//            BasicTextField(text = memo3, placeholder = "??????3", onChange = { memo3 = it })
            item {
                BasicTextField(
                    text = memoContent,
                    placeholder = "??????",
                    onChange = { memoContent = it })
            }
            item {
                PrimaryBigButton(text = "??????") {
                    val isValid = name.isNotBlank() || engName.isNotBlank() || email.isNotBlank()
                            || mobile.isNotBlank() || address.isNotBlank() || company.isNotBlank()
                            || team.isNotBlank() || role.isNotBlank()
                            || tel.isNotBlank() || memoContent.isNotBlank()

                    if (isValid) {
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

//                        val snapshot = ComposeView(ctx).apply {
//                            setContent {
//                                BusinessCardTemplate(
//                                    name, engName, email, mobile, address, team, role, company, null
//                                )
//                            }
//                        }
//                        val bitmap = convertView2Bitmap(cardImage)
                        val path = saveCardImage(ctx, cardImage!!, true)

                        val card = Card(
                            NayaCardId = 0,
                            name = name.ifBlank { null },
                            engName = engName.ifBlank { null },
                            kind = kind,
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
                            path = path,
                            templateId = 0
                        )

                        cardViewModel.addCard(card)
                        Toast.makeText(ctx, "?????? ????????? ??????????????????", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(ctx, "????????? ?????? ????????? ?????? ??????????????????", Toast.LENGTH_SHORT).show()
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

//@Composable
//@Preview
//fun BCardCreateScreenPreview() {
//    BCardCreateScreen(rememberNavController(), 1)
//}