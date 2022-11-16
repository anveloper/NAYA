package com.youme.naya.card

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.RecoverableSecurityException
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.youme.naya.components.OutlinedBigButton
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.database.entity.Card
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.share.ShareActivity
import com.youme.naya.ui.theme.fonts
import com.youme.naya.utils.convertPath2Uri
import com.youme.naya.widgets.home.ViewCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


lateinit var intentSenderLauncher: ActivityResultLauncher<IntentSenderRequest>

@Composable
fun CardDetailsDialog(
    activity: Activity,
    navController: NavHostController,
    nayaCard: ViewCard? = null,
    bCard: Card? = null,
    enableShare: Boolean = true,
    onDismissRequest: () -> Unit
) {
    val cardViewModel: CardViewModel = hiltViewModel()

    var isShareOpened by remember { mutableStateOf(false) }
    var isDeleteDialogOpened by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    intentSenderLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                coroutineScope.launch {
                    removeCardFileFromExternalStorage(activity, nayaCard?.uri ?: return@launch)
                    Toast.makeText(activity, "카드를 삭제했어요", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(activity, "Photo couldn't be deleted", Toast.LENGTH_SHORT).show()
        }
    }

    val shareLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        Log.i("Activity Result", it.resultCode.toString())
        when (it.resultCode) {
            RESULT_OK -> {
                isShareOpened = false
                onDismissRequest()
            }
            RESULT_CANCELED -> {
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
                            contentScale = ContentScale.Crop,
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
                        items(cardData) { item ->
                            if (!item[1].isNullOrBlank()) {
                                CardDetailsDescription(item[0]!!, item[1]!!)
                            }
                        }
                    }
                }

                if (enableShare) {
                    PrimaryBigButton(text = "공유하기") {
                        if (!isShareOpened) {
                            if (nayaCard != null && bCard == null) {
                                Log.i("Card", "Up ${nayaCard.uri}")
                                val intent = Intent(activity, ShareActivity::class.java)
                                intent.putExtra("cardUri", nayaCard.uri.toString())
                                intent.putExtra("filename", nayaCard.filename)
                                shareLauncher.launch(intent)
                            } else if (nayaCard == null && bCard != null) {
                                val bCardUri = convertPath2Uri(activity, bCard.path!!).toString()
                                Log.i("Card", "Up $bCardUri")
                                val intent = Intent(activity, ShareActivity::class.java)
                                intent.putExtra("cardUri", bCardUri)
                                intent.putExtra(
                                    "filename",
                                    bCard.path.substring(bCard.path.lastIndexOf('/') + 1)
                                )
                                shareLauncher.launch(intent)
                            }
                            isShareOpened = true
                        }
                    }
                }
                if (nayaCard == null && bCard != null) {
                    OutlinedBigButton(text = "수정하기") {
                        onDismissRequest()

                        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                        val jsonAdapter = moshi.adapter(Card::class.java).lenient()
                        val cardJson = jsonAdapter.toJson(bCard)

                        navController.navigate("bCardModify?card=$cardJson")
                    }
                }
                OutlinedBigButton(text = "삭제하기") {
                    isDeleteDialogOpened = true
                }
            }
        }
    }
    if (isDeleteDialogOpened) {
        DeleteAlertDialog(
            onDelete = {
                isDeleteDialogOpened = false
                onDismissRequest()

                if (nayaCard != null && bCard == null) {
                    coroutineScope.launch {
                        removeCardFileFromExternalStorage(activity, nayaCard.uri)
                    }
                } else if (nayaCard == null && bCard != null) {
                    cardViewModel.removeCard(bCard)
                    val imageFile = File(bCard.path!!)
                    if (imageFile.exists()) imageFile.delete()
                    Toast.makeText(activity, "카드를 삭제했어요", Toast.LENGTH_SHORT).show()
                }
            },
            onCancel = { isDeleteDialogOpened = false }
        )
    }
}

@Composable
fun CardDetailsDescription(
    fieldName: String,
    fieldValue: String
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = fieldName, fontFamily = fonts, fontWeight = FontWeight.Bold)
        Text(text = fieldValue, fontFamily = fonts, textAlign = TextAlign.End)
    }
}

suspend fun removeCardFileFromExternalStorage(context: Context, cardUri: Uri) {
    withContext(Dispatchers.IO) {
        try {
            context.contentResolver.delete(cardUri, null, null)
        } catch (e: SecurityException) {
            val intentSender = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    MediaStore.createDeleteRequest(
                        context.contentResolver,
                        listOf(cardUri)
                    ).intentSender
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                    val recoverableSecurityException = e as? RecoverableSecurityException
                    recoverableSecurityException?.userAction?.actionIntent?.intentSender
                }
                else -> null
            }
            intentSender?.let { sender ->
                intentSenderLauncher.launch(
                    IntentSenderRequest.Builder(sender).build()
                )
            }
        }
    }
}