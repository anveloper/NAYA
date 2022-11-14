package com.youme.naya.card

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.youme.naya.components.OutlinedBigButton
import com.youme.naya.ocr.DocumentScannerActivity
import com.youme.naya.ocr.StillImageActivity
import com.youme.naya.utils.convertUri2Path

@Composable
fun BusinessCardCreateDialog(navController: NavHostController, onDismissRequest: () -> Unit) {
    val context = LocalContext.current
    val activity = context as? Activity

    // OCR 액티비티 런처
    val ocrLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            // OCR 문자열 인식 결과
            val ocrResult = it.data?.getStringExtra("ocrResult")
            val imgPath = it.data?.getStringExtra("croppedImage")

            if (ocrResult.isNullOrBlank()) {
                Toast.makeText(context, "추출된 문자열이 없어요", Toast.LENGTH_SHORT).show()
            } else {
                navController.navigate("bCardCreateByCamera?result=${Uri.encode(ocrResult)}&path=${imgPath}")
            }
            onDismissRequest()
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

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            Modifier.wrapContentSize(),
            shape = RoundedCornerShape(12.dp),
            color = Color.White
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "어떤 방법으로 카드를 만들까요?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedBigButton(text = "카메라로 촬영하기") {
                    cameraLauncher.launch(
                        Intent(
                            activity,
                            DocumentScannerActivity::class.java
                        )
                    )
                }
                OutlinedBigButton(text = "갤러리에서 불러오기") {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    mediaLauncher.launch(intent)
                }
                OutlinedBigButton(text = "템플릿에서 만들기") {
                    onDismissRequest()
                    navController.navigate("bCardCreate")
                }
            }
        }
    }
}