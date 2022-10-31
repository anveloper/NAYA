package com.youme.naya.share

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youme.naya.BaseActivity
import com.youme.naya.ui.theme.*
import com.youme.naya.widgets.share.CircleWaveComp
import com.youme.naya.widgets.share.ShareCard
import com.youme.naya.widgets.share.ShareHeader
import com.youme.naya.widgets.share.nfc.NfcSendingResult
import com.youme.naya.widgets.share.nfc.SendingResult
import com.youme.naya.widgets.share.nfc.ShareNfcContent
import com.youme.naya.widgets.share.nfc.ShareNfcSearchFail
import kotlinx.coroutines.launch

class NfcActivityTest : BaseActivity(TransitionMode.HORIZON) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = LocalContext.current as? Activity
            AndroidTheme() {
                NfcShareScreen() {
                    activity?.finish()
                }
            }
        }
    }
}


// view
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NfcShareTestScreen(
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    val (isSearchFail, setIsSearchFail) = remember { mutableStateOf(false) }
    val (sendingResult, setSendingResult) = remember { mutableStateOf(SendingResult.Idle) }


    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed),
    )

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = sendingResult) {
        if (sendingResult != SendingResult.Idle) {
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            NfcSendingResult(sendingResult, { onFinish() }) {
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                    setSendingResult(SendingResult.Idle)
                }
            }
        },
        sheetShape = RoundedCornerShape(20.dp, 20.dp),
        sheetPeekHeight = 0.dp
    ) {
        Box(Modifier.fillMaxSize()) {
            // 배경 컴포넌트
            CircleWaveComp()
            ShareCard()
            // 헤더
            ShareHeader { onFinish() }
            // 컨텐츠
            ShareNfcContent()
            // 탐색 실패 시 다이얼로그
            if (isSearchFail) {
                ShareNfcSearchFail(onFinish = { onFinish() }) {
                    setIsSearchFail(false)
                }
            }

            // 화면 이동 테스트 버튼
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp), Arrangement.SpaceAround, Alignment.Bottom
            ) {
                TextButton(
                    onClick = { setIsSearchFail(true) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = SystemYellow)
                ) {
                    Text(text = "Not Found")
                }
                TextButton(onClick = {
                    setSendingResult(SendingResult.Fail)
                }, colors = ButtonDefaults.buttonColors(backgroundColor = SystemRed)) {
                    Text(text = "Fail", color = Color.White)
                }
                TextButton(onClick = {
                    setSendingResult(SendingResult.Success)
                }, colors = ButtonDefaults.buttonColors(backgroundColor = SystemGreen)) {
                    Text(text = "Success", color = Color.White)
                }
            }
        }
    }
}

@Preview(
    name = "naya Project share NFC", showBackground = true, showSystemUi = true
)
@Composable
fun NfcShareTestPreview() {
    NfcShareTestScreen() { Log.i("ShareActivity", "test") }
}
