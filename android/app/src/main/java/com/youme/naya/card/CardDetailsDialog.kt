package com.youme.naya.card

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.youme.naya.components.OutlinedBigButton
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.database.entity.Card
import com.youme.naya.share.ShareActivity
import com.youme.naya.ui.theme.fonts
import com.youme.naya.utils.convertPath2Uri
import com.youme.naya.widgets.home.ViewCard

@Composable
fun CardDetailsDialog(
    activity: Activity,
    navController: NavHostController,
    nayaCard: ViewCard? = null,
    bCard: Card? = null,
    onDismissRequest: () -> Unit
) {
    var isShareOpened by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        Log.i("Activity Result", it.resultCode.toString())
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                isShareOpened = false
                onDismissRequest()
            }
            Activity.RESULT_CANCELED -> {
                isShareOpened = false
                onDismissRequest()
            }
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
                if (nayaCard != null && bCard == null) {
                    Card(
                        Modifier
                            .padding(bottom = 16.dp)
                            .aspectRatio(5 / 9f),
                        shape = RectangleShape,
                        elevation = 4.dp
                    ) {
                        Image(
                            painter = rememberImagePainter(data = nayaCard.uri),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else if (nayaCard == null && bCard != null) {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .aspectRatio(9 / 5f),
                        shape = RectangleShape,
                        elevation = 4.dp
                    ) {
                        Image(
                            painter = rememberImagePainter(BitmapFactory.decodeFile(bCard.path)),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    val cardData = listOf(
                        listOf("이름", bCard.name),
                        listOf("영어 이름", bCard.engName),
                        listOf("이메일", bCard.email),
                        listOf("휴대폰 번호", bCard.mobile),
                        listOf("주소", bCard.address),
                        listOf("부서", bCard.team),
                        listOf("직책", bCard.role),
                        listOf("회사명", bCard.company),
                        listOf("메모", bCard.memoContent),
                    )

                    LazyColumn(
                        Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(bottom = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        itemsIndexed(cardData) { index, item ->
                            if (!item[1].isNullOrBlank()) {
                                CardDetailsDescription(item[0]!!, item[1]!!)
                            }
                        }
                    }
                }

                PrimaryBigButton(text = "공유하기") {
                    if (!isShareOpened) {
                        if (nayaCard != null && bCard == null) {
                            Log.i("Card", "Up ${nayaCard.uri}")
                            val intent = Intent(activity, ShareActivity::class.java)
                            intent.putExtra("cardUri", nayaCard.uri.toString())
                            intent.putExtra("filename", nayaCard.filename)
                            launcher.launch(intent)
                        } else if (nayaCard == null && bCard != null) {
                            val bCardUri = convertPath2Uri(activity, bCard.path!!).toString()
                            Log.i("Card", "Up $bCardUri")
                            val intent = Intent(activity, ShareActivity::class.java)
                            intent.putExtra("cardUri", bCardUri)
                            intent.putExtra(
                                "filename",
                                bCard.path.substring(bCard.path.lastIndexOf('/') + 1)
                            )
                            launcher.launch(intent)
                        }
                        isShareOpened = true
                    }
                }
                if (nayaCard == null && bCard != null) {
                    OutlinedBigButton(text = "수정하기") {

                    }
                }
                OutlinedBigButton(text = "삭제하기") {

                }
            }
        }
    }
}

@Composable
fun CardDetailsDescription(
    fieldName: String,
    fieldValue: String
) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = fieldName, fontFamily = fonts, fontWeight = FontWeight.Bold)
        Text(text = fieldValue, fontFamily = fonts, textAlign = TextAlign.End)
    }
}
