package com.youme.naya.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.R
import com.youme.naya.login.PermissionSheet
import com.youme.naya.ui.theme.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    permitted: Boolean,
    checkPermission: () -> Unit,
    signInGoogle: () -> Unit
) {
    val (confirmResult, setConfirmResult) = remember { mutableStateOf(ConfirmResult.Idle) }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    fun openSheet() = coroutineScope.launch {
        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
            bottomSheetScaffoldState.bottomSheetState.expand()
        } else {
            bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }


    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            PermissionSheet(permitted, { checkPermission() }) {
                setConfirmResult(ConfirmResult.Agree)
            }
        },
        sheetShape = RoundedCornerShape(20.dp, 20.dp),
        sheetPeekHeight = 0.dp
    ) {
        Surface(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center
            ) {
                LoginIconBox()
                Spacer(Modifier.height(12.dp))
                LoginInfoText()
                Image(
                    painter = painterResource(R.drawable.login_image),
                    contentDescription = "logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(1.2f)
                        .offset { IntOffset(100, 100) }
                        .padding(bottom = 16.dp)
                )
                Spacer(Modifier.height(52.dp))
                SignInGoogleButton(confirmResult,
                    { openSheet() }) { signInGoogle() }
            }
        }
    }
}

enum class ConfirmResult {
    Idle,
    Agree,
    Disagree
}

@Composable
fun LoginIconBox() {
    Box(Modifier.fillMaxWidth(), Alignment.TopStart) {
        Box(
            Modifier
                .size(68.dp)
                .shadow(3.dp, RoundedCornerShape(20.dp))
                .background(NeutralWhite, RoundedCornerShape(20.dp)), Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.login_naya_icon),
                contentDescription = "logo",
                modifier = Modifier
                    .size(60.dp)
                    .background(NeutralWhite, RoundedCornerShape(20.dp))
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun LoginInfoText() {
    Row(Modifier.fillMaxWidth(), Arrangement.Start, Alignment.CenterVertically) {
        Text(
            text = "NA",
            color = PrimaryBlue,
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            fontFamily = pico
        )
        Text(
            text = "를 소개하는",
            color = PrimaryDark,
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            fontFamily = fonts
        )
    }
    Row(Modifier.fillMaxWidth(), Arrangement.Start, Alignment.CenterVertically) {
        Text(
            text = "가장 쉬운 방법, ",
            color = PrimaryDark,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            fontFamily = fonts
        )
        Image(painterResource(R.drawable.home_logo_text), null)
    }
    Spacer(Modifier.height(8.dp))
    Text(
        text = "Naya와 함께 명함과 일정을 관리하고, \n나를 공유해보세요!",
        color = NeutralLight,
        modifier = Modifier.fillMaxWidth(),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        fontFamily = fonts
    )

}

@Composable
fun SignInGoogleButton(
    confirmResult: ConfirmResult, setConfirmResult: () -> Unit, onClick: () -> Unit
) {
    Box(Modifier.fillMaxWidth(), Alignment.Center) {
        TextButton(
            {
                if (confirmResult == ConfirmResult.Agree) {
                    onClick()
                } else {
                    Log.i("confirmResult", confirmResult.toString())
                    setConfirmResult()
                }

            },
            Modifier
                .fillMaxWidth(0.8f)
                .shadow(6.dp, RoundedCornerShape(12.dp))
                .background(NeutralLightness, RoundedCornerShape(12.dp)),

            ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                Arrangement.Center, Alignment.CenterVertically
            ) {
                Image(
                    painterResource(R.drawable.btn_google_logo),
                    "Google sign button",
                    modifier = Modifier.size(32.dp),
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "구글 계정으로 시작하기",
                    style = MaterialTheme.typography.overline,
                    color = PrimaryDark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fonts
                )
            }
        }
    }

}


@Preview(
    name = "Login Preview",
    showSystemUi = true,
    showBackground = true
)
@Composable
fun LoginScreenPreview() {
    LoginScreen(false, {}) {

    }
}