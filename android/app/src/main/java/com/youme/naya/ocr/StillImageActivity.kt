package com.youme.naya.ocr

import android.app.Activity
import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import android.view.ViewTreeObserver
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.annotation.KeepName
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.googlecode.tesseract.android.TessBaseAPI
import com.youme.naya.R
import org.sdase.submission.documentscanner.BitmapUtils
import org.sdase.submission.documentscanner.GraphicOverlay
import org.sdase.submission.documentscanner.VisionImageProcessor
import java.io.*

@KeepName
class StillImageActivity : AppCompatActivity() {
    private lateinit var context: Activity

    private var preview: ImageView? = null
    private var graphicOverlay: GraphicOverlay? = null
    private var selectedMode = TEXT_RECOGNITION_KOREAN
    private var selectedSize: String? = SIZE_SCREEN
    private var isLandScape = false
    private var imageUri: Uri? = null

    // Max width (portrait mode)
    private var imageMaxWidth = 0

    // Max height (portrait mode)
    private var imageMaxHeight = 0
    private var imageProcessor: VisionImageProcessor? = null

    // Tess
    lateinit var tess: TessBaseAPI
    var dataPath: String = ""

    // 이미지 경로
    private var savedImgAbsolutePath: String? = null

    // ML Kit
    private val recognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_still_image)

        context = this
        preview = findViewById(R.id.preview)

        // 4. 전달된 savedImgAbsolutePath가 잘 뜨는지 확인하기
        savedImgAbsolutePath = intent.getStringExtra("savedImgAbsolutePath")
        if (savedImgAbsolutePath != null) {
            val bitmap = BitmapFactory.decodeFile(savedImgAbsolutePath)
            preview?.setImageBitmap(bitmap)
            Log.i("savedImgAbsPathOnStill", savedImgAbsolutePath+"")

            // 5. R.id.preview 이미지를 통해 OCR 돌린다.
            // TextRecognize start
            val image = InputImage.fromBitmap(bitmap, 0)
            Log.i("image", "image is ok!!")

            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                // TODO 6. OCR 결과를 정규표현식(regex) 이용해 파싱한 후 용도에 맞게 save
                    Log.i("visionText", visionText.text)
                    val newIntent = Intent(context, StillImageActivity::class.java)
                    newIntent.putExtra("ocrResult", visionText.text)
                    newIntent.putExtra("croppedImage", savedImgAbsolutePath)
                    setResult(RESULT_OK, newIntent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.i("visionTextErr", e.toString())
                }
        } else {
            finish()
        }

//        dataPath = "$filesDir/assets/"
//
//        checkFile(File(dataPath + "tessdata/"), "kor")
//        checkFile(File(dataPath + "tessdata/"), "eng")
//
//        val lang: String = "kor+eng"
//        tess = TessBaseAPI()
//        tess.init(dataPath, lang)
//        if (savedImgAbsolutePath != null) {
//            processImage(BitmapFactory.decodeFile(savedImgAbsolutePath))
//        } else {
//            finish()
//        }

        isLandScape =
            resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        if (savedInstanceState != null) {
            imageUri =
                savedInstanceState.getParcelable(KEY_IMAGE_URI)
            imageMaxWidth =
                savedInstanceState.getInt(KEY_IMAGE_MAX_WIDTH)
            imageMaxHeight =
                savedInstanceState.getInt(KEY_IMAGE_MAX_HEIGHT)
            selectedSize =
                savedInstanceState.getString(KEY_SELECTED_SIZE)
        }

        val rootView = findViewById<View>(R.id.root)
        rootView.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    imageMaxWidth = rootView.width
                    imageMaxHeight =
                        rootView.height - findViewById<View>(R.id.control).height
                    if (SIZE_SCREEN == selectedSize) {
                        tryReloadAndDetectInImage()
                    }
                }
            })
    }

    private fun checkFile(dir: File, lang: String) {
        // 파일의 존재여부 확인 후 내부로 복사
        if (!dir.exists() && dir.mkdirs()) {
            copyFile(lang)
        }
        if (dir.exists()) {
            val datafilePath: String = "$dataPath/tessdata/$lang.traineddata"
            var dataFile: File = File(datafilePath)
            if (!dataFile.exists()) {
                copyFile(lang)
            }
        }
    }

    private fun copyFile(lang: String) {
        try {
            //언어데이타파일의 위치
            val filePath: String = "$dataPath/tessdata/$lang.traineddata"

            //AssetManager를 사용하기 위한 객체 생성
            val assetManager: AssetManager = assets;

            //byte 스트림을 읽기 쓰기용으로 열기
            val inputStream: InputStream = assetManager.open("tessdata/$lang.traineddata")
            val outStream: OutputStream = FileOutputStream(filePath)


            //위에 적어둔 파일 경로쪽으로 해당 바이트코드 파일을 복사한다.
            val buffer = ByteArray(1024)

            var read: Int = 0
            read = inputStream.read(buffer)
            while (read != -1) {
                outStream.write(buffer, 0, read)
                read = inputStream.read(buffer)
            }
            outStream.flush()
            outStream.close()
            inputStream.close()

        } catch (e: FileNotFoundException) {
            Log.v("오류발생", e.toString())
        } catch (e: IOException) {
            Log.v("오류발생", e.toString())
        }

    }

    private fun processImage(bitmap: Bitmap) {
        var ocrResult: String? = null;
        tess.setImage(bitmap)
        ocrResult = tess.utF8Text
        Log.i("ocrResult", ocrResult)

        val newIntent = Intent(context, StillImageActivity::class.java)
        newIntent.putExtra("ocrResult", ocrResult)
        newIntent.putExtra("croppedImage", savedImgAbsolutePath)
        setResult(RESULT_OK, newIntent)
        finish()
    }

    public override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        tryReloadAndDetectInImage()
    }

    public override fun onPause() {
        super.onPause()
        imageProcessor?.run {
            this.stop()
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        imageProcessor?.run {
            this.stop()
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(
            KEY_IMAGE_URI,
            imageUri
        )
        outState.putInt(
            KEY_IMAGE_MAX_WIDTH,
            imageMaxWidth
        )
        outState.putInt(
            KEY_IMAGE_MAX_HEIGHT,
            imageMaxHeight
        )
        outState.putString(
            KEY_SELECTED_SIZE,
            selectedSize
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            tryReloadAndDetectInImage()
        } else if (requestCode == REQUEST_CHOOSE_IMAGE && resultCode == Activity.RESULT_OK) {
            // In this case, imageUri is returned by the chooser, save it.
            imageUri = data!!.data
            tryReloadAndDetectInImage()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun tryReloadAndDetectInImage() {
        Log.d(
            TAG,
            "Try reload and detect image"
        )
        try {
            if (imageUri == null) {
                return
            }

            if (SIZE_SCREEN == selectedSize && imageMaxWidth == 0) {
                // UI layout has not finished yet, will reload once it's ready.
                return
            }

            val imageBitmap =
                BitmapUtils.getBitmapFromContentUri(contentResolver, imageUri) ?: return
            // Clear the overlay first
            graphicOverlay!!.clear()

            val resizedBitmap: Bitmap
            resizedBitmap = if (selectedSize == SIZE_ORIGINAL) {
                imageBitmap
            } else {
                // Get the dimensions of the image view
                val targetedSize: Pair<Int, Int> = targetedWidthHeight

                // Determine how much to scale down the image
                val scaleFactor = Math.max(
                    imageBitmap.width.toFloat() / targetedSize.first.toFloat(),
                    imageBitmap.height.toFloat() / targetedSize.second.toFloat()
                )
                Bitmap.createScaledBitmap(
                    imageBitmap,
                    (imageBitmap.width / scaleFactor).toInt(),
                    (imageBitmap.height / scaleFactor).toInt(),
                    true
                )
            }

            preview!!.setImageBitmap(resizedBitmap)
            if (imageProcessor != null) {
                graphicOverlay!!.setImageSourceInfo(
                    resizedBitmap.width, resizedBitmap.height, /* isFlipped= */false
                )
                imageProcessor!!.processBitmap(resizedBitmap, graphicOverlay)
            } else {
                Log.e(
                    TAG,
                    "Null imageProcessor, please check adb logs for imageProcessor creation error"
                )
            }
        } catch (e: IOException) {
            Log.e(
                TAG,
                "Error retrieving saved image"
            )
            imageUri = null
        }
    }

    private val targetedWidthHeight: Pair<Int, Int>
        get() {
            val targetWidth: Int
            val targetHeight: Int
            when (selectedSize) {
                SIZE_SCREEN -> {
                    targetWidth = imageMaxWidth
                    targetHeight = imageMaxHeight
                }
                else -> throw IllegalStateException("Unknown size")
            }
            return Pair(targetWidth, targetHeight)
        }

    companion object {
        private const val TAG = "StillImageActivity"
        private const val TEXT_RECOGNITION_KOREAN = "Text Recognition Korean (Beta)"

        private const val SIZE_SCREEN = "w:screen" // Match screen width
        private const val SIZE_ORIGINAL = "w:original" // Original image size
        private const val KEY_IMAGE_URI = "com.google.mlkit.vision.demo.KEY_IMAGE_URI"
        private const val KEY_IMAGE_MAX_WIDTH = "com.google.mlkit.vision.demo.KEY_IMAGE_MAX_WIDTH"
        private const val KEY_IMAGE_MAX_HEIGHT = "com.google.mlkit.vision.demo.KEY_IMAGE_MAX_HEIGHT"
        private const val KEY_SELECTED_SIZE = "com.google.mlkit.vision.demo.KEY_SELECTED_SIZE"
        private const val REQUEST_IMAGE_CAPTURE = 1001
        private const val REQUEST_CHOOSE_IMAGE = 1002
    }
}
