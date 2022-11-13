package com.youme.naya.ocr

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.youme.naya.R
import org.sdase.submission.documentscanner.DocumentScanner
import org.sdase.submission.documentscanner.constants.ResponseType
import java.io.File
import java.io.FileOutputStream

class DocumentScannerActivity : AppCompatActivity() {
    private lateinit var croppedImageView: ImageView

    private val documentScanner = DocumentScanner(
        this,
        { croppedImageResults ->
            // display the first cropped image
            croppedImageView.setImageBitmap(BitmapFactory.decodeFile(croppedImageResults.first()))
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
        setContentView(R.layout.activity_test)

        // cropped image
        croppedImageView = findViewById(R.id.cropped_image_view)

        // ocr button
        val go_ocr = findViewById<Button>(R.id.ocr_btn)

        // start document scan
        documentScanner.startScan()

        // start OCR
        val croppedImg = intent.getIntExtra("croppedImg", 1)
        val activity = this // 액티비티 자체여서

        Log.i("cropped Image Bitmap", croppedImg.toString())

        go_ocr.setOnClickListener {
//            val intent = Intent(activity, StillImageActivity::class.java)
            val intent = Intent(activity, DocumentScannerActivity::class.java)

            // 1. R.id.cropped_image_view에 들어있는 이미지를 저장한다.
            val bitmap: Bitmap? = getBitmapFromView(croppedImageView)
            var savedImgAbsolutePath: String? = null

            if (bitmap != null) {
                savedImgAbsolutePath = saveBitmapToJpeg(bitmap, "nayatempfile", this.baseContext)
            }

            // 2. 저장된 이미지의 경로를 인텐트에 저장한다.
            intent.putExtra("savedImgAbsolutePath", savedImgAbsolutePath)

            // 3. 정상적으로 처리했음을 이전 액티비티에 알리고 종료한다.
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}


fun getBitmapFromView(v: View): Bitmap? {
    val b = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
    val c = Canvas(b)
    v.draw(c)
    return b
}

private fun saveBitmapToJpeg(bitmap: Bitmap, name: String, context: Context): String? {

    //내부저장소 캐시 경로를 받아옵니다.
    val storage: File = context.cacheDir

    //저장할 파일 이름
    val fileName = "$name.jpg"

    //storage 에 파일 인스턴스를 생성합니다.
    val tempFile = File(storage, fileName)
    try {

        // 자동으로 빈 파일을 생성합니다.
        tempFile.createNewFile()

        // 파일을 쓸 수 있는 스트림을 준비합니다.
        val out = FileOutputStream(tempFile)

        // compress 함수를 사용해 스트림에 비트맵을 저장합니다.
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)

        // 스트림 사용후 닫아줍니다.
        out.close()

        Log.i("saveBitmapToJpeg", tempFile.absolutePath)
        return tempFile.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


