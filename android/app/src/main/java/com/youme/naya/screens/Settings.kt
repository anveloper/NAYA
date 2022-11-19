package com.youme.naya.screens


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.R
import com.youme.naya.intro.IntroViewModel
import com.youme.naya.login.PermissionViewModel
import com.youme.naya.ui.theme.*

@Composable
fun SettingsScreen(
    introViewModel: IntroViewModel,
    permissionViewModel: PermissionViewModel
) {
    val (openPolicy, setOpenPolicy) = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NeutralWhite),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp), Arrangement.spacedBy(8.dp),
            Alignment.Start
        ) {

            SettingsText(
                "About Naya",
                24.sp,
            )
            Spacer(Modifier.height(16.dp))
            TextButton(onClick = {
                introViewModel.resetIsFirst()
                introViewModel.loadIsFirst()
            }, Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    SettingsText("도움말", 20.sp)
                    Icon(Icons.Outlined.ArrowBackIosNew, null, Modifier.size(24.dp), PrimaryDark)
                }
            }
            TextButton(onClick = {
                setOpenPolicy(true)
            }, Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    SettingsText("약관 및 개인정보 처리 동의", 20.sp)
                    Icon(Icons.Outlined.ArrowBackIosNew, null, Modifier.size(24.dp), NeutralMetal)
                }
            }
            TextButton(onClick = { /*TODO*/ }, Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    SettingsText("앱버전", 20.sp)
                    Icon(Icons.Outlined.ArrowBackIosNew, null, Modifier.size(24.dp), NeutralMetal)
                }
            }
            TextButton(onClick = { /*TODO*/ }, Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    SettingsText("팀원 소개", 20.sp)
                    Icon(Icons.Outlined.ArrowBackIosNew, null, Modifier.size(24.dp), NeutralMetal)
                }
            }
            Spacer(Modifier.height(120.dp))
            Row(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterVertically) {
                Text("Copyright by")
                Image(painterResource(R.drawable.home_logo_text), null)
            }
        }
    }
    if (openPolicy) {
        AlertDialog(onDismissRequest = { setOpenPolicy(false) }, buttons = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(420.dp),
                Arrangement.SpaceEvenly,
                Alignment.CenterHorizontally
            ) {
                Text(
                    text = "이용약관",
                    color = PrimaryDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    fontFamily = fonts
                )
                Box(
                    Modifier
                        .fillMaxWidth(0.96f)
                        .border(
                            width = 1.dp,
                            color = NeutralLightness,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .fillMaxHeight(0.30f),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth(0.9f)
                            .fillMaxHeight(0.9f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = permissionViewModel.termsText.value,
                            color = NeutralGray,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            fontFamily = fonts
                        )
                    }
                }
                Text(
                    text = "개인정보 처리방침",
                    color = PrimaryDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    fontFamily = fonts
                )
                Box(
                    Modifier
                        .fillMaxWidth(0.96f)
                        .border(
                            width = 1.dp,
                            color = NeutralLightness,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .fillMaxHeight(0.60f),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth(0.9f)
                            .fillMaxHeight(0.9f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = permissionViewModel.privacyText.value,
                            color = NeutralGray,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            fontFamily = fonts
                        )
                    }

                }
            }
        })
    }

}


@Composable
fun SettingsText(content: String, fontSize: TextUnit) {
    Text(
        content,
        Modifier.fillMaxWidth(),
        PrimaryDark,
        fontSize,
        FontStyle.Normal,
        FontWeight.Bold,
        fonts
    )
}