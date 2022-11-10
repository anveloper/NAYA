package com.youme.naya.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.youme.naya.card.CustomCardStackView
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.ocr.DocumentScannerActivity
import com.youme.naya.ocr.StillImageActivity
import com.youme.naya.ui.theme.NeutralLightness
import com.youme.naya.ui.theme.NeutralMedium
import com.youme.naya.ui.theme.NeutralWhite
import com.youme.naya.ui.theme.PrimaryBlue
import com.youme.naya.widgets.common.NayaBcardSwitchButtons


@Composable
fun NuyaCardHolderScreen(navController: NavHostController) {
    val cardViewModel: CardViewModel = hiltViewModel()
    var isBCard by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            SearchInput()
            NayaBcardSwitchButtons(
                nayaTab = {
                    isBCard = false
                    CustomCardStackView(cardViewModel, false)
                },
                bCardTab = {
                    isBCard = true
                    CustomCardStackView(cardViewModel, true)
                }
            )
        }
        if (isBCard) {
            MultiFloatingActionButton(navController)
        }
    }
}

/**
 * 검색 창 컴포저블
 */
@Composable
fun SearchInput() {
    var textState by remember {
        mutableStateOf(TextFieldValue())
    }
    val source = remember {
        MutableInteractionSource()
    }
    var focused by remember {
        mutableStateOf(false)
    }
    val focusRequester by remember {
        mutableStateOf(FocusRequester())
    }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = textState, onValueChange = { textState = it },
        singleLine = true,
        interactionSource = source,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { focused = it.isFocused },
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }),
    ) { innerTextField ->
        Row(
            Modifier
                .background(NeutralLightness, RoundedCornerShape(percent = 20))
                .padding(16.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = Icons.Outlined.Search.name,
                modifier = Modifier.padding(end = 16.dp)
            )

            if (!focused && textState.text.isEmpty()) {
                Text("이름, 전화번호, 회사명, 직책으로 검색", color = NeutralMedium)
            }
            innerTextField()
        }
    }
}

/**
 * 우측 하단에 위치한 카드 추가 버튼
 */
@Composable
fun MultiFloatingActionButton(
    navController: NavHostController
) {
    val context = LocalContext.current
    val activity = context as? Activity

    // OCR 액티비티 런처
    val ocrLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                // OCR 문자열 인식 결과
                val ocrResult = it.data?.getStringExtra("ocrResult")

                if (ocrResult.isNullOrBlank()) {
                    Toast.makeText(context, "추출된 문자열이 없어요", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate("bCardCreateByCamera?result=${Uri.encode(ocrResult)}")
                }
            }
            Activity.RESULT_CANCELED -> {
            }
        }
    }
    // 카메라 액티비티 런처
    val cameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            when (it.resultCode) {
                Activity.RESULT_OK -> {
                    // 임시 이미지 저장 경로
                    val imgPath = it.data?.getStringExtra("savedImgAbsolutePath")
                    val ocrIntent = Intent(activity, StillImageActivity::class.java)
                    ocrIntent.putExtra("savedImgAbsolutePath", imgPath)
                    ocrLauncher.launch(ocrIntent)
                }
                Activity.RESULT_CANCELED -> {
                }
            }
        }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.BottomEnd
    ) {
        Row(
            modifier = Modifier.padding(bottom = 64.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FloatingActionButton(
                onClick = {
//                    cameraLauncher.launch(
//                        Intent(
//                            activity,
//                            DocumentScannerActivity::class.java
//                        )
//                    )
                    getTempOCRResult(navController)
                },
                backgroundColor = PrimaryBlue,
                contentColor = NeutralWhite
            ) {
                Icon(Icons.Filled.AddAPhoto, Icons.Filled.AddAPhoto.toString())
            }
            FloatingActionButton(
                onClick = { navController.navigate("bCardCreate") },
                backgroundColor = PrimaryBlue,
                contentColor = NeutralWhite
            ) {
                Icon(Icons.Filled.Keyboard, Icons.Filled.AddAPhoto.toString())
            }
        }
    }
}

@Composable
@Preview
fun NuyaCardHolderScreenPreview() {
    NuyaCardHolderScreen(rememberNavController())
}

fun getTempOCRResult(navController: NavHostController) {
    val ocrResult = "문자열 1\n" +
            "문자열 2\n" +
            "" +
            "문자열 3\n" +
            "" +
            "문자열 4"
    navController.navigate("bCardCreateByCamera?result=${Uri.encode(ocrResult)}")
}