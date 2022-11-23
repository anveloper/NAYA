package com.youme.naya.screens


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.youme.naya.R
import com.youme.naya.intro.IntroViewModel
import com.youme.naya.login.PermissionViewModel
import com.youme.naya.ui.theme.*

@Composable
fun SettingsScreen(
    navController: NavHostController,
    introViewModel: IntroViewModel,
    permissionViewModel: PermissionViewModel
) {
    val (openPolicy, setOpenPolicy) = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NeutralWhite),
    ) {
        Column() {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .padding(horizontal = 24.dp), Arrangement.spacedBy(8.dp),
                Alignment.Start
            ) {
                SettingsText(
                    "About Naya",
                    style = Typography.h3,
                    color = PrimaryDark,
                    fontFamily = pico,
                )
                Spacer(Modifier.height(16.dp))
                TextButton(onClick = {
                    introViewModel.resetIsFirst()
                    introViewModel.loadIsFirst()
                }, Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.fillMaxWidth(),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        SettingsText("도움말", color = NeutralGray, style = Typography.h6, fonts)
                        Icon(
                            Icons.Outlined.ArrowForwardIos,
                            null,
                            Modifier.size(18.dp),
                            NeutralLight
                        )
                    }
                }
                TextButton(onClick = {
                    setOpenPolicy(true)
                }, Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.fillMaxWidth(),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        SettingsText(
                            "약관 및 개인정보 처리 동의",
                            color = NeutralGray,
                            style = Typography.h6,
                            fonts
                        )
                        Icon(
                            Icons.Outlined.ArrowForwardIos,
                            null,
                            Modifier.size(18.dp),
                            NeutralLight
                        )
                    }
                }
                TextButton(
                    onClick = { navController.navigate("team") },
                    Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth(),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically) {
                        SettingsText("Naya 팀 소개",
                            color = NeutralGray,
                            style = Typography.h6,
                            fonts)
                        Icon(Icons.Outlined.ArrowForwardIos, null,
                            Modifier.size(18.dp),
                            NeutralLight)
                    }
                }
//            TextButton(onClick = { /*TODO*/ }, Modifier.fillMaxWidth()) {
//                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
//                    SettingsText("앱버전", 20.sp)
//                    Icon(Icons.Outlined.ArrowForwardIos, null, Modifier.size(24.dp), NeutralMetal)
//                }
//            }
            }

            Column(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterHorizontally) {
                Image(
                    painterResource(R.drawable.home_logo_text),
                    null,
                    Modifier.size(120.dp, 40.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "© Copyright 2022. YouMe. All Rights Reserved.",
                    fontSize = 8.sp,
                    fontFamily = fonts,
                    color = NeutralGray
                )
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
fun SettingsText(content: String, color : Color, style: TextStyle, fontFamily: FontFamily?,) {
    Text(
        content,
        Modifier,
        style = style,
        color = color,
        fontFamily = fontFamily

    )
}