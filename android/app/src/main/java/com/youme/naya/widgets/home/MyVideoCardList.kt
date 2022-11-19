package com.youme.naya.widgets.home


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.youme.naya.R
import com.youme.naya.custom.VideoCardActivity
import com.youme.naya.ui.theme.NeutralGray
import com.youme.naya.utils.convertUri2Path
import com.youme.naya.widgets.items.CardItemPlus
import com.youme.naya.widgets.items.SupportCard
import com.youme.naya.widgets.items.VideoItem


@Composable
fun MyVideoCardList(context: Context, navController: NavHostController) {
    // 처음 아이템의 padding을 정해주기 위한 식
    val context = LocalContext.current
    val activity = context as? Activity

    val viewModel = viewModel<CardListViewModel>()

    val videoCard = viewModel.videoCard.value
    val supportCard = viewModel.supportCard.value
    LaunchedEffect(1) {
        viewModel.fetchVideoCard()
        viewModel.fetchSupportCard()
    }

    // 미디어 카드 액티비티 런처
    val mediaCameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        Log.i("Media Card Custom", it.resultCode.toString())
        if (it.resultCode == Activity.RESULT_OK) {
            viewModel.fetchNayaCards()
            viewModel.fetchVideoCard()
            viewModel.fetchSupportCard()
        }
    }
    // 비디오 선택 액티비티
    val videoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data as Uri
            val videoPath = convertUri2Path(context, uri)
            val videoUri = uri.toString()
            val mediaIntent = Intent(activity, VideoCardActivity::class.java)
            mediaIntent.putExtra("savedVideoAbsolutePath", videoPath)
            mediaIntent.putExtra("savedVideoUri", videoUri)
            mediaCameraLauncher.launch(mediaIntent)
        }
    }


    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(Modifier.fillMaxWidth(), Alignment.Center) {
            if (videoCard != null) {
                Log.i("VideoCard", videoCard.uri.toString())
                Box(Modifier.fillMaxWidth(), Alignment.Center) {
                    VideoItem(videoCard)
                }
                if (supportCard != null) {
                    Log.i("VideoCard", supportCard.uri.toString())
                    Box(Modifier.fillMaxWidth(), Alignment.Center) {
                        SupportCard(supportCard)
                    }
                } else {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent), Alignment.Center
                    ) {
                        Box(
                            Modifier
                                .width(200.dp)
                                .height(360.dp)
                                .background(Color.Transparent)
                        ) {
                            Image(
                                painterResource(R.drawable.frame_video),
                                null,
                                Modifier
                                    .fillMaxSize(),
                            )
                        }
                    }
                }
            } else {
                CardItemPlus(navController)
            }
        }


        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterVertically) {
            if (videoCard == null) {
                Text(text = "비디오 카드를 등록해 보세요", color = NeutralGray)
            } else {
                Text(text = "가장 최근 미디어 입니다.", color = NeutralGray)
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "video/*"
                    videoLauncher.launch(intent)
                }) {
                    Icon(Icons.Outlined.Create, null)
                }
            }
        }
    }
}
