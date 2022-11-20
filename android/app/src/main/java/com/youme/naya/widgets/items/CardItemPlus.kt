package com.youme.naya.widgets.items

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.youme.naya.R
import com.youme.naya.card.BusinessCardCreateDialog
import com.youme.naya.components.OutlinedBigButton
import com.youme.naya.custom.MediaCardActivity
import com.youme.naya.custom.VideoCardActivity
import com.youme.naya.ocr.StillImageActivity
import com.youme.naya.utils.convertUri2Path
import com.youme.naya.widgets.home.CardListViewModel

private val CardModifier = Modifier
    .width(200.dp)
    .height(360.dp)
    .shadow(elevation = 6.dp)

@Composable
fun CardItemPlus(
    navController: NavHostController = rememberNavController(),
    isBCard: Boolean = false,
    isNuya: Boolean = false,
    test: Boolean = false
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val viewModel = viewModel<CardListViewModel>()

    var bCardCreateDialog by remember { mutableStateOf(false) }

    // 미디어 카드 액티비티 런처
    val mediaCameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        Log.i("Media Card Custom", it.resultCode.toString())
        if (it.resultCode == RESULT_OK) {
            viewModel.fetchNayaCards()
        }
    }
    // 이미지 선택 액티비티
    val mediaLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val uri = it.data?.data as Uri
            val imgPath = convertUri2Path(context, uri)
            val mediaIntent = Intent(activity, MediaCardActivity::class.java)
            mediaIntent.putExtra("savedImgAbsolutePath", imgPath)
            mediaCameraLauncher.launch(mediaIntent)
        }
    }
    // 비디오 선택 액티비티
    val videoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val uri = it.data?.data as Uri
            val videoPath = convertUri2Path(context, uri)
            val videoUri = uri.toString()
            val mediaIntent = Intent(activity, VideoCardActivity::class.java)
            mediaIntent.putExtra("savedVideoAbsolutePath", videoPath)
            mediaIntent.putExtra("savedVideoUri", videoUri)
            mediaCameraLauncher.launch(mediaIntent)
        }
    }
    // OCR 액티비티 런처
    val ocrLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            // OCR 문자열 인식 결과
            val ocrResult = it.data?.getStringExtra("ocrResult")
            val imgPath = it.data?.getStringExtra("croppedImage")

            if (ocrResult.isNullOrBlank()) {
                Toast.makeText(context, "추출된 문자열이 없어요", Toast.LENGTH_SHORT).show()
            } else {
                navController.navigate("bCardCreateByCamera?result=${Uri.encode(ocrResult)}&path=${imgPath}&isNuya=false")
            }
        }
    }
    // 명함 인식 카메라 액티비티 런처
    val businessCameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                // 임시 이미지 저장 경로
                val imgPath = it.data?.getStringExtra("savedImgAbsolutePath")
                val ocrIntent = Intent(activity, StillImageActivity::class.java)
                ocrIntent.putExtra("savedImgAbsolutePath", imgPath)
                ocrLauncher.launch(ocrIntent)
            }
        }

    val (imgSelector, setImgSelector) = remember { mutableStateOf(false) }

    Card(CardModifier) {
        IconButton(onClick = {
            if (isBCard) {
                bCardCreateDialog = true
            } else {
                if (isNuya) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    mediaLauncher.launch(intent)
                } else {
                    setImgSelector(true)
                }
//                mediaCameraLauncher.launch(Intent(activity, MediaCardActivity::class.java))
            }
        }) {
            Image(
                painter = painterResource(R.drawable.card_icon_plus),
                contentDescription = if (isBCard) "import business card" else "import naya card",
            )
        }

    }

    if (imgSelector) {
        AlertDialog({ setImgSelector(false) }, {
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
                    mediaCameraLauncher.launch(
                        Intent(
                            activity,
                            MediaCardActivity::class.java
                        )
                    )
                    setImgSelector(false)
                }
                OutlinedBigButton(text = "갤러리에서 불러오기") {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    mediaLauncher.launch(intent)
                    setImgSelector(false)
                }
                if (test && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    OutlinedBigButton("Beta") {
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "video/*"
                        videoLauncher.launch(intent)
                        setImgSelector(false)
                    }
                }
            }
        }, Modifier.wrapContentSize(),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.White
        )
    }
    if (bCardCreateDialog) {
        BusinessCardCreateDialog(navController = navController, isNuya = isNuya) {
            bCardCreateDialog = false
        }
    }

}