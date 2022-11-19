package com.youme.naya.card

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.youme.naya.R
import com.youme.naya.utils.rotateBitmap

@ExperimentalMaterialApi
@Composable
fun FlippableCard(
    cardFace: CardFace,
    onClick: (CardFace) -> Unit,
    modifier: Modifier = Modifier,
    axis: RotationAxis = RotationAxis.AxisY,
    isHorizontalCard: Boolean = false,
    back: @Composable (() -> Unit)? = null,
    front: @Composable () -> Unit = {}
) {
    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
    )
    Card(
        onClick = { onClick(cardFace) },
        modifier = modifier
            .graphicsLayer {
                if (axis == RotationAxis.AxisX) {
                    rotationX = rotation.value
                } else {
                    rotationY = rotation.value
                }
                cameraDistance = 12f * density
            }
            .aspectRatio(if (isHorizontalCard) 9 / 5f else 5 / 9f),
        shape = RectangleShape,
        elevation = 4.dp
    ) {
        if (rotation.value <= 90f) {
            Box {
                front()
            }
        } else {
            Box(Modifier.graphicsLayer {
                if (axis == RotationAxis.AxisX) {
                    rotationX = 180f
                } else {
                    rotationY = 180f
                }
            }) {
                if (back == null) {
                    if (isHorizontalCard) {
                        Image(
                            painter = painterResource(id = R.drawable.sample_card_horizontal),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.sample_card),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    back()
                }
            }
        }
    }
}