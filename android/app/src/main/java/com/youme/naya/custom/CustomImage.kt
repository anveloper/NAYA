package com.youme.naya.custom

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import com.youme.naya.ui.theme.*
import kotlin.math.roundToInt

@Composable
fun CustomImage(
    bitmap: Bitmap
) {

    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    Box(
        Modifier
            .fillMaxSize()
            .zIndex(100f)
            .background(Color.Black)
    ) {
        Image(
            bitmap.asImageBitmap(), null,
            Modifier
                .fillMaxSize()
                .transformable(state = state)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y
                )
        )
        // tools
        CardInfoTools()

        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp), Alignment.Center
        ) {
            Text(
                text = " ✌ 사진 수정 ☝ 글자 수정 ",
                fontFamily = fonts,
                color = PrimaryDark,
                fontSize = 12.sp,
                modifier = Modifier
                    .background(NeutralLightness, RoundedCornerShape(4.dp))
            )
        }

    }
}

data class TextItem(
    var content: String,
    var fontColor: Color = PrimaryBlue,
    var offsetX: Float = 0.0f,
    var offsetY: Float = 0.0f,
    var rotate: Float = 0.0f,
    var fontSize: Int = 24,
)

data class StickerItem(
    var image: Int,
    var offsetX: Float = 0.0f,
    var offsetY: Float = 0.0f,
    var rotate: Float = 0.0f,
    var scale: Float = 24f,
)


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CardInfoTools() {
    Box(
        Modifier
            .fillMaxSize(), Alignment.Center
    ) {
        var texts = rememberSaveable { mutableListOf<TextItem>() }
        var stickers = rememberSaveable { mutableListOf<StickerItem>() }
        val keyboardController = LocalSoftwareKeyboardController.current
        // 정보 붙히는 곳
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            CardFrame(texts, stickers)
        }
        // 정보 내용 수정하는 곳
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            Alignment.BottomCenter
        ) {
            var (newContent, setNewContent) = remember { mutableStateOf<String>("") }
            Row(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterVertically) {
                TextField(
                    value = newContent,
                    placeholder = { Text(text = "카드에 넣을 내용 입력") },
                    onValueChange = {
                        setNewContent(it)
                    },
                    trailingIcon = {
                        IconButton(onClick = { setNewContent("") }) {
                            Icon(Icons.Outlined.Clear, null)
                        }
                    },
                    modifier = Modifier
                        .scale(0.8f)
                        .border(
                            BorderStroke(
                                width = 4.dp,
                                brush = PrimaryGradientBrush
                            ),
                        ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = NeutralWhiteTrans,
                        focusedIndicatorColor = PrimaryDark,
                        unfocusedIndicatorColor = NeutralGray
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        if (newContent.trim().isNotEmpty())
                            texts.add(TextItem(newContent))
                        setNewContent("")
                        keyboardController?.hide()
                    }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
                IconButton(onClick = { /*TODO*/ }) {

                }
            }
        }
    }
}


@Composable
fun CardFrame(
    texts: List<TextItem>, stickers: List<StickerItem>
) {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Box(
            Modifier
                .width(300.dp)
                .height(540.dp)
                .padding(5.dp)
                .border(5.dp, PrimaryGradientBrush, RectangleShape)
                .padding(8.dp)
        ) {

            texts.forEach { item ->
                if (item.content.isNotEmpty()) {
                    var offsetX by remember { mutableStateOf(item.offsetX) }
                    var offsetY by remember { mutableStateOf(item.offsetX) }

                    var (isSelected, setIsSelected) = remember { mutableStateOf(false) }

                    var (content, setContent) = remember { mutableStateOf(item.content) }
                    var (fontSize, setFontSize) = remember { mutableStateOf(item.fontSize) }
                    var (rotate, setRotate) = remember { mutableStateOf(item.rotate) }
                    var (fontColor, setFontColor) = remember { mutableStateOf(item.fontColor) }
                    if (isSelected) {
                        FontTool(
                            setIsSelected,
                            content,
                            setContent,
                            fontSize,
                            setFontSize,
                            rotate,
                            setRotate,
                            fontColor,
                            setFontColor
                        )
                    }
                    Box(Modifier.matchParentSize(), Alignment.Center) {
                        Text(
                            content,
                            Modifier
                                .rotate(rotate)
                                .offset {
                                    IntOffset(
                                        offsetX.roundToInt(),
                                        offsetY.roundToInt()
                                    )
                                }
                                .clickable {
                                    Log.i(content, "isSelected")
                                    setIsSelected(true)
                                }
                                .pointerInput(Unit) {
                                    detectDragGestures { change, dragAmount ->
                                        change.consumeAllChanges()
                                        offsetX += dragAmount.x
                                        offsetY += dragAmount.y
                                        Log.i(content, "$offsetX $offsetY")
                                    }
                                },
                            fontColor,
                            fontSize.sp,
                            FontStyle.Normal,
                            FontWeight.Normal,
                            fonts
                        )
                    }
                }
            }

            stickers.forEach { item ->
                if (item.image != 0) {
                    var offsetX by remember { mutableStateOf(item.offsetX) }
                    var offsetY by remember { mutableStateOf(item.offsetX) }

                    var (isSelected, setIsSelected) = remember { mutableStateOf(false) }

                    var (image, setImage) = remember { mutableStateOf(item.image) }
                    var (scale, setScale) = remember { mutableStateOf(item.scale) }
                    var (rotate, setRotate) = remember { mutableStateOf(item.rotate) }
                    if (isSelected) {
                        StickerTool(
                            setIsSelected,
                            image,
                            setImage,
                            scale,
                            setScale,
                            rotate,
                            setRotate
                        )
                    }
                    Box(Modifier.matchParentSize(), Alignment.Center) {
                        Image(
                            painterResource(image),
                            null,
                            Modifier
                                .rotate(rotate)
                                .offset {
                                    IntOffset(
                                        offsetX.roundToInt(),
                                        offsetY.roundToInt()
                                    )
                                }
                                .clickable {
                                    Log.i(image.toString(), "isSelected")
                                    setIsSelected(true)
                                }
                                .pointerInput(Unit) {
                                    detectDragGestures { change, dragAmount ->
                                        change.consumeAllChanges()
                                        offsetX += dragAmount.x
                                        offsetY += dragAmount.y
                                        Log.i(image.toString(), "$offsetX $offsetY")
                                    }
                                },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StickerTool(
    setIsSelected: (Boolean) -> Unit,
    image: Int,
    setImage: (Int) ->Unit,
    scale: Float,
    setScale: (Float) -> Unit,
    rotate: Float,
    setRotate: (Float) -> Unit,
) {

}

@Composable
fun FontTool(
    setIsSelected: (Boolean) -> Unit,
    content: String,
    setContent: (String) -> Unit,
    fontSize: Int,
    setFontSize: (Int) -> Unit,
    rotate: Float,
    setRotate: (Float) -> Unit,
    fontColor: Color,
    setFontColor: (Color) -> Unit
) {
    AlertDialog(
        onDismissRequest = { setIsSelected(false) },
        buttons = {
            Box(
                Modifier
                    .width(300.dp)
                    .height(540.dp)
                    .padding(8.dp), Alignment.Center
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(NeutralGrayTrans, RoundedCornerShape(12.dp)),
                    Alignment.TopCenter
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            setContent("")
                            setIsSelected(false)
                        }) {
                            Icon(Icons.Outlined.Delete, null, tint = NeutralLight)
                        }
                        IconButton(onClick = { setIsSelected(false) }) {
                            Icon(Icons.Outlined.Clear, null, tint = NeutralLight)
                        }
                    }

                }
                var newRotate by remember { mutableStateOf(rotate) }
                var newFontSize by remember { mutableStateOf(fontSize.toFloat()) }
                var newFontColor by remember { mutableStateOf(fontColor) }

                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text(
                        content,
                        Modifier
                            .sizeIn(20.dp)
                            .rotate(newRotate),
                        newFontColor,
                        newFontSize.toInt().sp,
                        FontStyle.Normal,
                        FontWeight.Normal,
                        fonts
                    )
                }
                Column(
                    Modifier
                        .fillMaxSize(),
                    Arrangement.Bottom,
                    Alignment.CenterHorizontally
                ) {
                    // 글시 회전
                    Row(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterVertically) {
                        Text(text = "회전")
                        Slider(
                            value = newRotate,
                            onValueChange = {
                                newRotate = it
                            },
                            onValueChangeFinished = {
                                setRotate(newRotate)
                            },
                            valueRange = -180f..180f,
                            steps = 360,
                            colors = SliderDefaults.colors(
                                thumbColor = PrimaryLight,
                                activeTrackColor = Color.Transparent
                            )
                        )
                    }
                    // 폰트 사이즈 수정
                    Row(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterVertically) {
                        Text(text = "크기")
                        Slider(
                            value = newFontSize,
                            onValueChange = {
                                newFontSize = it
                            },
                            onValueChangeFinished = {
                                setFontSize(newFontSize.toInt())
                            },
                            valueRange = 12f..48f,
                            steps = 36,
                            colors = SliderDefaults.colors(
                                thumbColor = PrimaryLight,
                                activeTrackColor = Color.Transparent
                            )
                        )
                    }

                    ClassicColorPicker(
                        color = newFontColor,
                        onColorChanged = { color: HsvColor ->
                            newFontColor = color.toColor()
                            setFontColor(color.toColor())
                            Log.i("color", color.toColor().toString())
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )

                }
            }

        },

        backgroundColor = Color.Transparent,
        shape = RoundedCornerShape(20.dp)
    )
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