package com.youme.naya.share

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Keep
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.youme.naya.BaseActivity
import com.youme.naya.R
import com.youme.naya.network.RetrofitClient
import com.youme.naya.network.RetrofitService
import com.youme.naya.ui.theme.*
import com.youme.naya.vo.SendCardRequestVO
import com.youme.naya.vo.SendCardResponseVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

@Keep
class ShareActivity : BaseActivity(TransitionMode.VERTICAL) {

    private lateinit var retrofit: Retrofit
    private lateinit var supplementService: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        val auth = FirebaseAuth.getInstance()
        val uid = auth.uid
        Log.i("Share Firebase uid", uid.toString())

        initRetrofit()

        super.onCreate(savedInstanceState)

        KakaoSdk.init(this, getString(R.string.kakao_app_key))

        setContent {
            val cardUri = intent.getStringExtra("cardUri")
            var filename = intent.getStringExtra("filename")
            val activity = LocalContext.current as? Activity
            val (cardId, setCardId) = remember { mutableStateOf<Int?>(-1) }
            val (sharedUri, setSharedUri) = remember { mutableStateOf<Uri?>(null) }
            if (cardUri == null) {
                Toast.makeText(this, "???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show()
                finish()
            } // uri????????? ??????
            // Share Loading tmp
            val (isLoading, setIsLoading) = remember { mutableStateOf(true) }

            if (uid != null && cardUri != null && filename != null) {
                if (sharedUri == null) {
                    if (filename == "") filename =
                        "NAYA-MEDIA-" + System.currentTimeMillis().toString() + ".png"

                    uploadFirebase(uid, cardUri, filename) { uri ->
                        setSharedUri(uri)
                    }
                } else {
                    Log.i("Firebase Shared Uri", sharedUri.toString())
                    if (cardId == -1) {
                        supplementService.sendCard(
                            SendCardRequestVO(
                                uid,
                                sharedUri.toString()
                            )
                        )
                            .enqueue(object : Callback<SendCardResponseVO> {
                                override fun onFailure(
                                    call: Call<SendCardResponseVO>,
                                    t: Throwable
                                ) {
                                    Log.d("TAG", "?????? : {$t}")
                                }

                                override fun onResponse(
                                    call: Call<SendCardResponseVO>,
                                    response: Response<SendCardResponseVO>
                                ) {
                                    Log.i("SHARE BACKEND CALL", call.toString())
                                    Log.i("SHARE BACKEND RESPONSE", response.toString())
                                    Log.i(
                                        "SHARE SEND CARD ID",
                                        response.body()?.sendCardId.toString()
                                    )
                                    setCardId(response.body()?.sendCardId)
                                    setIsLoading(false)
                                }
                            })
                    }
                }
            } else {
                Log.i("Share Some", "is null")
            }
            AndroidTheme() {

                ShareScreen(cardUri, uid, cardId) {
                    intent.putExtra("finish", 0)
                    setResult(RESULT_OK, intent)
                    activity?.finish()
                }
                if (isLoading) {
                    // tmp loading box
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color(0xDD000000))
                            .clickable(enabled = false) {}, // ??????
                        Alignment.Center
                    ) {
                        Column(Modifier.fillMaxWidth(0.8f)) {
                            Text(
                                text = "????????? ??????????????????.\n????????? ??????????????????.",
                                color = NeutralLightness,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                fontFamily = fonts
                            ) // ??????
                            Spacer(Modifier.height(4.dp))
                            LinearProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp),
                                backgroundColor = NeutralMedium,
                                color = NeutralLightness
                            ) // ??????
                        }
                    }
                    // ?????? ?????? ??????
                }
            }
        }
    }

    private fun initRetrofit() {
        retrofit = RetrofitClient.getInstance()
        supplementService = retrofit.create(RetrofitService::class.java)
    }

    private fun uploadFirebase(
        uid: String,
        uri: String,
        filename: String,
        uploadComplete: (Uri) -> Unit
    ): Unit {
        val storage = FirebaseStorage.getInstance("gs://naya-365407.appspot.com")
        Log.i("Share Firebase uid", uid)
        Log.i("Share Card Uri", uri)
        Log.i("Share Card Filename", filename)
        val shareFilename = "SHARE-$filename"
        storage.reference
            .child("naya/$uid")
            .child(shareFilename)
            .putFile(uri.toUri())
            .addOnCompleteListener() {
                if (it.isSuccessful) {
                    storage.reference.child("naya/$uid").child(shareFilename).downloadUrl
                        .addOnSuccessListener { uri ->
                            uploadComplete(uri)
                        }.addOnFailureListener { e ->
                            Log.i("upload failed", e.toString())
                        }
                } else {
                    Log.i("upload failed", "from firebase")
                }
            }

    }

}


private val ShareContainerModifier = Modifier
    .fillMaxSize()
    .background(color = Color(0xFFFFFFFF))

private val ShareTitleModifier = Modifier
    .fillMaxWidth()
    .height(64.dp)

@Composable
fun ShareScreen(
    cardUri: String?,
    uid: String?,
    cardId: Int?,
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        when (it.resultCode) {
            Activity.RESULT_OK -> {

            }
            Activity.RESULT_CANCELED -> {

            }
        }
    }


    Column(ShareContainerModifier, Arrangement.SpaceBetween, Alignment.CenterHorizontally) {
        Box(
            ShareTitleModifier,
            contentAlignment = Alignment.Center
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                IconButton(
                    onClick = {
                        onFinish()
                    }) {
                    Icon(
                        Icons.Outlined.ArrowBackIos,
                        "move to start",
                        tint = Color(0xFFCED3D6)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.home_tab_naya),
                    contentDescription = null
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    color = Color(0xFF122045),
                    fontSize = 16.sp,
                    text = "????????????",
                    fontFamily = fonts
                )
            }
        }
        ShareTextButton(
            R.drawable.ic_share_qr,
            "QR?????? ??????",
            "Naya ?????? ????????? QR????????? ???????????? ???????????????"
        ) {
            // QR?????? ??????
            var intent = Intent(activity, QrActivity::class.java)
            intent.putExtra("uid", uid)
            intent.putExtra("cardId", cardId)
            launcher.launch(intent)
        }
//        ShareTextButton(
//            R.drawable.ic_share_nfc,
//            "NFC ??????",
//            "NFC??? ???????????? ?????? ??????????????? ????????? ????????????"
//        ) {
//            var intent = Intent(activity, NfcActivity::class.java)
//            intent.putExtra("userId", 123)
//            launcher.launch(intent)
//        }
        ShareTextButton(
            R.drawable.ic_share_kakao,
            "???????????? ??????",
            "?????????????????? ?????? ????????? ?????? ??? ?????????"
        ) {
            // ???????????? ???????????? ??????
            //????????? ??????
            Log.i("log", "????????? ?????? ??????")
            val defaultScrap = FeedTemplate(
                content = Content(
                    title = "NAYA ????????? ???????????????!",
                    description = "??? ??????????????? NAYA ????????? ????????? ??? ????????????!",
                    imageUrl = "https://firebasestorage.googleapis.com/v0/b/naya%2D365407.appspot.com/o/main%5Fnaya.png?alt=media",
                    link = Link(
                        webUrl = "https://k7b104.p.ssafy.io/$uid/$cardId",
                        mobileWebUrl = "https://k7b104.p.ssafy.io/$uid/$cardId"
                    )
                ), buttonTitle = "????????? ??????"
            )

            // ????????? ???????????? URL
            val url = "https://k7b104.p.ssafy.io/$uid/$cardId"

            // ???????????? ???????????? ??????
            if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
                // ?????????????????? ???????????? ?????? ??????
                ShareClient.instance.shareDefault(context, defaultScrap) { sharingResult, error ->
                    if (error != null) {
                        Log.e(TAG, "???????????? ?????? ??????", error)
                    } else if (sharingResult != null) {
                        Log.d(TAG, "???????????? ?????? ?????? ${sharingResult.intent}")
//                        startActivity(sharingResult.intent)
                        launcher.launch(sharingResult.intent)

                        // ???????????? ????????? ??????????????? ?????? ?????? ???????????? ????????? ?????? ?????? ???????????? ?????? ???????????? ?????? ??? ????????????.
                        Log.w(TAG, "Warning Msg: ${sharingResult.warningMsg}")
                        Log.w(TAG, "Argument Msg: ${sharingResult.argumentMsg}")
                    }
                }
            } else {
                // ???????????? ?????????: ??? ?????? ?????? ??????
                // ??? ?????? ?????? ??????
                val sharerUrl = WebSharerClient.instance.makeScrapUrl(url)

                // CustomTabs?????? ??? ???????????? ??????

                // 1. CustomTabsServiceConnection ?????? ???????????? ??????
                // ex) Chrome, ?????? ?????????, FireFox, ?????? ???
                try {
                    KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
                } catch (e: UnsupportedOperationException) {
                    // CustomTabsServiceConnection ?????? ??????????????? ?????? ??? ????????????
                    Toast.makeText(activity, "?????? ????????? ??????????????? ????????????.", Toast.LENGTH_SHORT).show()
                }

                // 2. CustomTabsServiceConnection ????????? ???????????? ??????
                // ex) ??????, ????????? ???
                try {
                    KakaoCustomTabsClient.open(context, sharerUrl)
                } catch (e: ActivityNotFoundException) {
                    // ??????????????? ????????? ????????? ??????????????? ?????? ??? ????????????
                    Toast.makeText(activity, "????????? ??????????????? ????????????.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        ShareTextButton(
            R.drawable.ic_share_insta,
            "Instagram ??????",
            "Instagram ???????????? ????????? ????????? ??????????????????"
        ) {
            // SNS ??????
            try {
                if (cardUri != null && activity != null) {
                    val intent = Intent("com.instagram.share.ADD_TO_STORY")
                    val sourceApplication = "${R.string.facebook_app_id}"
                    intent.putExtra("source_application", sourceApplication)
                    val backgroundAssetUri = Uri.parse(cardUri)
                    intent.setDataAndType(backgroundAssetUri, "image/*")
                    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    launcher.launch(intent)
                }
            } catch (e: Exception) {
                Toast.makeText(activity, "?????????????????? ??????, ????????? ????????????", Toast.LENGTH_SHORT).show()
            }
        }
        Spacer(Modifier.height(8.dp))
//        ShareExtra()
    }
}


@Composable
fun ShareTextButton(
    imageId: Int,
    title: String,
    content: String,
    fn: () -> Unit
) {
    TextButton(modifier = Modifier
        .width(280.dp)
        .height(108.dp)
        .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
        .background(color = PrimaryLight, shape = RoundedCornerShape(12.dp)),
        onClick = { fn() }) {
        Row(
            Modifier.fillMaxSize(),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(imageId),
                contentDescription = ""
            )
            Spacer(Modifier.width(4.dp))
            Column(Modifier.padding(8.dp)) {
                Text(
                    color = PrimaryDark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    text = title,
                    fontFamily = fonts
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    color = NeutralMetal,
                    fontSize = 12.sp,
                    text = content,
                    fontFamily = fonts
                )
            }
        }
    }
}

@Composable
fun ShareIconButton(
    imageId: Int,
    title: String,
    fn: () -> Unit
) {
    Column(
        Modifier.padding(horizontal = 4.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(24.dp))
                .background(brush = PrimaryGradientBrush, shape = RoundedCornerShape(24.dp)),
            onClick = { fn() }
        ) {
            Image(
                painterResource(imageId),
                contentDescription = ""
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            color = Color(0xFFA1ACB3),
            fontSize = 12.sp,
            text = title
        )
    }
}


@Preview(
    name = "naya Project share",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun sharePreview() {
    ShareScreen("", "", 1) { Log.i("ShareActivity", "test") }
}
