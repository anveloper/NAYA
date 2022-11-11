package com.youme.naya.screens


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.youme.naya.card.CustomCardListView
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.ocr.DocumentScannerActivity
import com.youme.naya.ocr.StillImageActivity
import com.youme.naya.ui.theme.NeutralWhite
import com.youme.naya.ui.theme.PrimaryBlue
import com.youme.naya.utils.convertUri2Path
import com.youme.naya.widgets.common.NayaBcardSwitchButtons

@Composable
fun NayaCardScreen(navController: NavHostController) {
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
            NayaBcardSwitchButtons(
                nayaTab = {
                    isBCard = false
                    CustomCardListView(cardViewModel, false)
                },
                bCardTab = {
                    isBCard = true
                    CustomCardListView(cardViewModel, true)
                }
            )
        }
        if (isBCard) {
            NayaFloatingActionButtons(navController)
        }
    }
}

/**
 * 우측 하단에 위치한 카드 추가 버튼
 */
@Composable
fun NayaFloatingActionButtons(
    navController: NavHostController
) {
    val context = LocalContext.current
    val activity = context as? Activity

    // OCR 액티비티 런처
    val ocrLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            // OCR 문자열 인식 결과
            val ocrResult = it.data?.getStringExtra("ocrResult")

            if (ocrResult.isNullOrBlank()) {
                Toast.makeText(context, "추출된 문자열이 없어요", Toast.LENGTH_SHORT).show()
            } else {
                navController.navigate("bCardCreateByCamera?result=${Uri.encode(ocrResult)}")
            }
        }
    }
    // 카메라 액티비티 런처
    val cameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                // 임시 이미지 저장 경로
                val imgPath = it.data?.getStringExtra("savedImgAbsolutePath")
                val ocrIntent = Intent(activity, StillImageActivity::class.java)
                ocrIntent.putExtra("savedImgAbsolutePath", imgPath)
                ocrLauncher.launch(ocrIntent)
            }
        }
    // 이미지 선택 액티비티
    val mediaLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data as Uri
            val imgPath = convertUri2Path(context, uri)
            val ocrIntent = Intent(activity, StillImageActivity::class.java)
            ocrIntent.putExtra("savedImgAbsolutePath", imgPath)
            ocrLauncher.launch(ocrIntent)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.BottomEnd
    ) {
        Row(
            modifier = Modifier.padding(bottom = 80.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FloatingActionButton(
                onClick = {
                    cameraLauncher.launch(
                        Intent(
                            activity,
                            DocumentScannerActivity::class.java
                        )
                    )
                },
                backgroundColor = PrimaryBlue,
                contentColor = NeutralWhite
            ) {
                Icon(Icons.Filled.AddAPhoto, Icons.Filled.AddAPhoto.toString())
            }
            FloatingActionButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    mediaLauncher.launch(intent)
                },
                backgroundColor = PrimaryBlue,
                contentColor = NeutralWhite
            ) {
                Icon(Icons.Filled.Image, Icons.Filled.Image.toString())
            }
            FloatingActionButton(
                onClick = { navController.navigate("bCardCreate") },
                backgroundColor = PrimaryBlue,
                contentColor = NeutralWhite
            ) {
                Icon(Icons.Filled.Keyboard, Icons.Filled.Keyboard.toString())
            }
        }
    }
}

@Preview
@Composable
fun NayaCardScreenPreview() {
    NayaCardScreen(rememberNavController())
}
