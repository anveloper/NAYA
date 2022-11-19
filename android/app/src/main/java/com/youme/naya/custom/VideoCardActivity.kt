package com.youme.naya.custom

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.youme.naya.BaseActivity
import com.youme.naya.ui.theme.*
import com.youme.naya.utils.saveVideoRaw

class VideoCardActivity : BaseActivity(TransitionMode.HORIZON) {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = LocalContext.current as? Activity
            val savedVideoAbsolutePath = intent.getStringExtra("savedVideoAbsolutePath")
            val savedVideoUri = intent.getStringExtra("savedVideoUri")
            if (savedVideoAbsolutePath != null) {
                Log.i("savedVideoAbsolutePath", savedVideoAbsolutePath)
            } else {
                finish()
            }
            if (savedVideoUri != null) {
                Log.i("savedVideoUri", savedVideoUri)
            } else {
                finish()
            }
            AndroidTheme() {
                VideoCardScreen(savedVideoUri!!.toUri(),
                    { bitmap ->
//                        saveCardImage(baseContext, bitmap)
                        intent.putExtra("Custom Exit", 1)
                        setResult(RESULT_OK, intent)
                        activity?.finish()
                    },
                    {
                        intent.putExtra("finish", 0)
                        setResult(RESULT_OK, intent)
                        activity?.finish()
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun VideoCardScreen(
    savedVideoUri: Uri,
    createNayaCardFile: (Bitmap) -> Unit,
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    val (isLoading, setIsLoading) = remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Box(
            Modifier
                .width(300.dp)
                .height(540.dp)
                .background(NeutralGray)
        ) {
            val context = LocalContext.current
//            val surfaceView = SurfaceView(boxContext)
//            val holder = surfaceView.holder
//            val player = MediaPlayer.create(boxContext, savedVideoUri)
//            val recorder = MediaRecorder(boxContext)

            val exoPlayer = remember {
                ExoPlayer.Builder(context)
                    .build()
                    .apply {
                        val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                        val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                            context,
                            defaultDataSourceFactory
                        )
                        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(MediaItem.fromUri(savedVideoUri))

                        setMediaSource(source)
                        prepare()
                    }
            }

            exoPlayer.playWhenReady = true
            exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
            exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

            DisposableEffect(
                AndroidView(factory = {
                    PlayerView(context).apply {
                        hideController()
                        useController = false
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

                        player = exoPlayer
                        layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    }
                })
            ) {
                onDispose { exoPlayer.release() }
            }

        }
        Row(Modifier.fillMaxSize(), Arrangement.SpaceBetween, Alignment.Top) {
            TextButton(
                onClick = {
                    onFinish()
                }) {
                Icon(
                    Icons.Outlined.ArrowBackIos,
                    "move to start",
                    tint = NeutralLight
                )
                Text(text = "돌아가기", fontFamily = fonts, color = NeutralLight)
            }
            TextButton(
                onClick = {
                    setIsLoading(true)
                    Handler(Looper.getMainLooper()).postDelayed({
                        saveVideoRaw(context, savedVideoUri)
                    }, 4000)
                    onFinish()
                }) {
                Text(text = "완료", fontFamily = fonts, color = PrimaryBlue)
                Icon(
                    Icons.Outlined.ArrowForwardIos,
                    "move to start",
                    tint = PrimaryBlue
                )
            }
            if (isLoading) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color(0xDD000000))
                        .clickable(enabled = false) {}, // 임시
                    Alignment.Center
                ) {
                    Column(Modifier.fillMaxWidth(0.8f)) {
                        Text(
                            text = "영상을 저장 중입니다.\n잠시만 기다려주세요.",
                            color = NeutralLightness,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            fontFamily = fonts
                        ) // 임시
                        Spacer(Modifier.height(4.dp))
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            backgroundColor = NeutralMedium,
                            color = NeutralLightness
                        ) // 임시
                    }
                }
            }
        }
    }
}