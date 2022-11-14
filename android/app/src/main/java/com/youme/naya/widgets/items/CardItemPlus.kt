package com.youme.naya.widgets.items

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.youme.naya.R
import com.youme.naya.card.BusinessCardCreateDialog
import com.youme.naya.custom.MediaCardActivity
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
    isBCard: Boolean = false
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
            viewModel.fetchCards()
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
                navController.navigate("bCardCreateByCamera?result=${Uri.encode(ocrResult)}&path=${imgPath}")
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
                setImgSelector(true)
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
        AlertDialog(onDismissRequest = { setImgSelector(false) }, {
            TextButton(onClick = {
                mediaCameraLauncher.launch(
                    Intent(
                        activity,
                        MediaCardActivity::class.java
                    )
                )
                setImgSelector(false)
            }) {
                Text("카메라")
            }
            TextButton(onClick = {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                mediaLauncher.launch(intent)
                setImgSelector(false)
            }) {
                Text("갤러리")
            }
        })
    }
    if (bCardCreateDialog) {
        BusinessCardCreateDialog(navController = navController) {
            bCardCreateDialog = false
        }
    }

}