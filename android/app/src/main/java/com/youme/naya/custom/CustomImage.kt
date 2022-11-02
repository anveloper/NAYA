package com.youme.naya.custom

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.components.BasicTextField
import com.youme.naya.ui.theme.PrimaryBlue
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.PrimaryGradientBrush
import com.youme.naya.ui.theme.fonts
import kotlin.math.roundToInt

@Composable
fun CustomImage(bitmap: Bitmap) {

    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    Box(Modifier.fillMaxSize()) {
        Image(
            bitmap.asImageBitmap(), null,
            Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .transformable(state = state)
                .fillMaxSize()
        )
        CardInfoTools()
    }
}

data class InfoItem(
    var content: String,
    var fontColor: Color = PrimaryBlue,
    var offsetX: Float = 0.0f,
    var offsetY: Float = 0.0f,
    var fontSize: Int = 24,
)

@Composable
fun CardInfoTools() {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        var items = rememberSaveable {
            mutableListOf<InfoItem>(InfoItem("안성진", PrimaryBlue), InfoItem("#ISFJ", PrimaryDark))
        }

        // 정보 붙히는 곳
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            CardFrame(items)
        }
        // 정보 내용 수정하는 곳
        Box(
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            Alignment.BottomCenter
        ) {
            var (newContent, setNewContent) = remember {
                mutableStateOf("")
            }
            Column() {
                BasicTextField(text = newContent, placeholder = "새로운 내용", onChange = setNewContent)
                IconButton(onClick = { items.add(InfoItem(newContent)) }) {
                    Icon(
                        Icons.Outlined.Add,
                        "move to start",
                        tint = Color(0xFFCED3D6)
                    )
                }
            }
        }
    }
}


@Composable
fun CardFrame(items: List<InfoItem>) {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Box(
            Modifier
                .width(250.dp)
                .height(450.dp)
                .padding(5.dp)
                .border(5.dp, PrimaryGradientBrush, RectangleShape)
                .padding(8.dp)
        ) {


            // 미디어 명함 테두리
            items.forEach { item ->
                Box(Modifier.matchParentSize(), Alignment.Center) {
                    var offsetX by remember { mutableStateOf(item.offsetX) }
                    var offsetY by remember { mutableStateOf(item.offsetX) }
                    Text(
                        item.content,
                        Modifier
                            .offset {
                                IntOffset(
                                    offsetX.roundToInt(),
                                    offsetY.roundToInt()
                                )
                            }
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consumeAllChanges()
                                    offsetX += dragAmount.x
                                    offsetY += dragAmount.y
                                    Log.i("${item.content}", "$offsetX $offsetY")
                                }
                            },
                        item.fontColor,
                        item.fontSize.sp,
                        FontStyle.Normal,
                        FontWeight.Normal,
                        fonts
                    )
                }
            }
        }
    }

}


@Preview(
    name = "CardInfoTools",
    showBackground = true,
    showSystemUi = false
)
@Composable
fun CardInfoToolsPreview() {
    CardInfoTools()
}