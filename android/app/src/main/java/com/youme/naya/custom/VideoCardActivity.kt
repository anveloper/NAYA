package com.youme.naya.custom

import android.app.Activity
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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

    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Box(
            Modifier
                .width(300.dp)
                .height(540.dp)
                .background(NeutralGray)
        ) {
            val boxContext = LocalContext.current
            val surfaceView = SurfaceView(boxContext)
            val holder = surfaceView.holder
            val player = MediaPlayer.create(boxContext, savedVideoUri)
            val recorder = MediaRecorder(boxContext)
//            DisposableEffect(
//                AndroidView(factory = {
//                    PlayerView(boxContext).apply {
//                        hideController()
//                        useController = false
//                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
//
//                        player = exoPlayer
//                        layoutParams = FrameLayout.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.MATCH_PARENT
//                        )
//
//                    }
//                })
//            ) {
//                onDispose { exoPlayer.release() }
//            }
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

                }) {
                Text(text = "완료", fontFamily = fonts, color = PrimaryBlue)
                Icon(
                    Icons.Outlined.ArrowForwardIos,
                    "move to start",
                    tint = PrimaryBlue
                )
            }
        }
    }
}