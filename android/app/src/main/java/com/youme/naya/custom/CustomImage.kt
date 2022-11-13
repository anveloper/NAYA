package com.youme.naya.custom

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddReaction
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Gesture
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
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
    var size: Int = 60,
)

data class DrawItem(
    var draw: ImageBitmap,
    var offsetX: Float = 0.0f,
    var offsetY: Float = 0.0f,
    var rotate: Float = 0.0f,
    var size: Int = 200,
)


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CardInfoTools() {
    Box(
        Modifier
            .fillMaxSize(), Alignment.Center
    ) {
        val texts = rememberSaveable { mutableListOf<TextItem>() }
        val stickers = rememberSaveable { mutableListOf<StickerItem>() }
        val draws = rememberSaveable { mutableListOf<DrawItem>() }
        val keyboardController = LocalSoftwareKeyboardController.current
        // 정보 붙히는 곳
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            CardFrame(texts, stickers, draws)
        }
        // 정보 내용 수정하는 곳
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            Alignment.BottomCenter
        ) {
            val (newContent, setNewContent) = remember { mutableStateOf("") }
            val (newSticker, setNewSticker) = remember { mutableStateOf(false) }
            val (newDraw, setNewDraw) = remember { mutableStateOf(false) }
            Row(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterVertically) {
                IconButton(
                    onClick = { setNewDraw(true) },
                    Modifier
                        .scale(0.8f)
                        .border(
                            BorderStroke(
                                width = 4.dp,
                                brush = PrimaryGradientBrush,
                            ), CircleShape
                        )
                ) {
                    Icon(
                        Icons.Outlined.Gesture,
                        null,
                        tint = PrimaryBlue
                    )
                }
                IconButton(
                    onClick = { setNewSticker(true) },
                    Modifier
                        .scale(0.8f)
                        .border(
                            BorderStroke(
                                width = 4.dp,
                                brush = PrimaryGradientBrush,
                            ), CircleShape
                        )
                ) {
                    Icon(
                        Icons.Outlined.AddReaction,
                        null,
                        tint = PrimaryBlue
                    )
                }
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
            }
            if (newDraw) {
                DrawBoxDialog({ setNewDraw(false) }) {
                    draws.add(DrawItem(it))
                }
            }
            if (newSticker) {
                StickerDialog({ setNewSticker(false) }) { image ->
                    stickers.add(StickerItem(image))
                }
            }
        }
    }
}


@Composable
fun CardFrame(
    texts: List<TextItem>, stickers: List<StickerItem>, draws: List<DrawItem>
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

                    val (isSelected, setIsSelected) = remember { mutableStateOf(false) }

                    val (content, setContent) = remember { mutableStateOf(item.content) }
                    val (fontSize, setFontSize) = remember { mutableStateOf(item.fontSize) }
                    val (rotate, setRotate) = remember { mutableStateOf(item.rotate) }
                    val (fontColor, setFontColor) = remember { mutableStateOf(item.fontColor) }
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
                                        change.consume()
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

                    val (isSelected, setIsSelected) = remember { mutableStateOf(false) }

                    val (image, setImage) = remember { mutableStateOf(item.image) }
                    val (size, setSize) = remember { mutableStateOf(item.size) }
                    val (rotate, setRotate) = remember { mutableStateOf(item.rotate) }
                    if (isSelected) {
                        StickerTool(
                            setIsSelected,
                            image,
                            setImage,
                            size,
                            setSize,
                            rotate,
                            setRotate
                        )
                    }
                    Box(Modifier.matchParentSize(), Alignment.Center) {

                        Image(
                            painterResource(image),
                            null,
                            Modifier
                                .size(size.dp)
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
                                        change.consume()
                                        offsetX += dragAmount.x
                                        offsetY += dragAmount.y
                                        Log.i(image.toString(), "$offsetX $offsetY")
                                    }
                                },
                        )
                    }
                }
            }

            draws.forEach { item ->
                if (item.draw != null) {
                    var offsetX by remember { mutableStateOf(item.offsetX) }
                    var offsetY by remember { mutableStateOf(item.offsetX) }

                    val (isSelected, setIsSelected) = remember { mutableStateOf(false) }

                    val (image, setImage) = remember { mutableStateOf(item.draw) }
                    val (size, setSize) = remember { mutableStateOf(item.size) }
                    val (rotate, setRotate) = remember { mutableStateOf(item.rotate) }
                    if (isSelected) {
                        DrawTool(
                            setIsSelected,
                            image,
                            setImage,
                            size,
                            setSize,
                            rotate,
                            setRotate
                        )
                    }
                    Box(Modifier.matchParentSize(), Alignment.Center) {

                        Image(
                            image,
                            null,
                            Modifier
                                .size(size.dp)
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
                                        change.consume()
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
fun DrawTool(
    setIsSelected: (Boolean) -> Unit,
    image: ImageBitmap,
    setImage: (ImageBitmap) -> Unit,
    size: Int,
    setSize: (Int) -> Unit,
    rotate: Float,
    setRotate: (Float) -> Unit,
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
                            setSize(0)
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
                var newSize by remember { mutableStateOf(size.toFloat()) }

                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Image(
                        image,
                        null,
                        Modifier
                            .size(newSize.dp)
                            .rotate(newRotate)
                    )
                }
                Column(
                    Modifier
                        .fillMaxSize(),
                    Arrangement.Bottom,
                    Alignment.CenterHorizontally
                ) {
                    // 글시 회전
                    Row(
                        Modifier.fillMaxWidth(0.8f),
                        Arrangement.Center,
                        Alignment.CenterVertically
                    ) {
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
                    Row(
                        Modifier.fillMaxWidth(0.8f),
                        Arrangement.Center,
                        Alignment.CenterVertically
                    ) {
                        Text(text = "크기")
                        Slider(
                            value = newSize,
                            onValueChange = {
                                newSize = it
                            },
                            onValueChangeFinished = {
                                setSize(newSize.toInt())
                            },
                            valueRange = 10f..150f,
                            steps = 142,
                            colors = SliderDefaults.colors(
                                thumbColor = PrimaryLight,
                                activeTrackColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        },
        backgroundColor = Color.Transparent,
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable
fun StickerTool(
    setIsSelected: (Boolean) -> Unit,
    image: Int,
    setImage: (Int) -> Unit,
    size: Int,
    setSize: (Int) -> Unit,
    rotate: Float,
    setRotate: (Float) -> Unit,
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
                            setSize(0)
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
                var newSize by remember { mutableStateOf(size.toFloat()) }

                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Image(
                        painterResource(image),
                        null,
                        Modifier
                            .size(newSize.dp)
                            .rotate(newRotate)
                    )
                }
                Column(
                    Modifier
                        .fillMaxSize(),
                    Arrangement.Bottom,
                    Alignment.CenterHorizontally
                ) {
                    // 글시 회전
                    Row(
                        Modifier.fillMaxWidth(0.8f),
                        Arrangement.Center,
                        Alignment.CenterVertically
                    ) {
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
                    // 스티커 사이즈 수정
                    Row(
                        Modifier.fillMaxWidth(0.8f),
                        Arrangement.Center,
                        Alignment.CenterVertically
                    ) {
                        Text(text = "크기")
                        Slider(
                            value = newSize,
                            onValueChange = {
                                newSize = it
                            },
                            onValueChangeFinished = {
                                setSize(newSize.toInt())
                            },
                            valueRange = 10f..150f,
                            steps = 142,
                            colors = SliderDefaults.colors(
                                thumbColor = PrimaryLight,
                                activeTrackColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        },
        backgroundColor = Color.Transparent,
        shape = RoundedCornerShape(20.dp)
    )
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
                    Row(
                        Modifier.fillMaxWidth(0.8f),
                        Arrangement.Center,
                        Alignment.CenterVertically
                    ) {
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
                    Row(
                        Modifier.fillMaxWidth(0.8f),
                        Arrangement.Center,
                        Alignment.CenterVertically
                    ) {
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