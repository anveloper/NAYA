package com.youme.naya.ocr

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.youme.naya.BaseActivity
import com.youme.naya.ui.theme.PrimaryBlue
import org.sdase.submission.documentscanner.DocumentScanner
import org.sdase.submission.documentscanner.constants.ResponseType

class DocumentScannerActivity : BaseActivity(TransitionMode.NONE) {
    private lateinit var croppedImage: MutableState<Bitmap?>

    private val documentScanner = DocumentScanner(
        this,
        { croppedImageResults ->
            // display the first cropped image
            croppedImage.value = BitmapFactory.decodeFile(croppedImageResults.first())
        },
        {
            // an error happened
                errorMessage ->
            Log.v("documentscannerlogs", errorMessage)
        },
        {
            // user canceled document scan
            Log.v("documentscannerlogs", "User canceled document scan")
        },
        ResponseType.IMAGE_FILE_PATH,
        true,
        1
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        documentScanner.startScan()
        setContent {
            croppedImage = remember { mutableStateOf<Bitmap?>(null) }
            Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
                croppedImage.value?.let {
                    // 이제 여기서 이미지를 보여주는 창을 띄웠으니,
                    // 다시 저 bitmap 들고 액티비티 이동
                    // activity finish() 로직 들어가야 함
                    Image(
                        it.asImageBitmap(), null,
                        Modifier
                            .border(
                                3.dp,
                                PrimaryBlue,
                            )
                    )
                    Text(text = "Scanner!!")
                    TextButton(onClick = {

                    }) {
                        Text("다음은 OCR")
                    }
                }
            }
        }
    }
}


