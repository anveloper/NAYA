package com.youme.naya.custom

import android.content.Context
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import com.youme.naya.ui.theme.*
import dev.shreyaspatil.capturable.controller.CaptureController
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
            .background(Color.Black)
    ) {
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CardInfoTools() {
    Box(
        Modifier
            .fillMaxSize(), Alignment.Center
    ) {
        var items = rememberSaveable { mutableListOf<InfoItem>() }

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
            var (newColor, setNewColor) = remember { mutableStateOf<Color>(PrimaryBlue) }
            var (newContent, setNewContent) = remember { mutableStateOf<String>("") }
            Column() {
                val keyboardController = LocalSoftwareKeyboardController.current

                TextField(
                    value = newContent,
                    onValueChange = {
                        setNewContent(it)
                    },
                    trailingIcon = {
                        IconButton(onClick = { setNewContent("") }) {
                            Icon(Icons.Outlined.Clear, null)
                        }
                    },
                    modifier = Modifier.border(
                        BorderStroke(
                            width = 4.dp,
                            brush = PrimaryGradientBrush
                        ),
                        shape = RoundedCornerShape(50)
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        if (newContent.trim().isNotEmpty())
                            items.add(InfoItem(newContent))
                        setNewContent("")
                        keyboardController?.hide()
                    }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
            }
        }
    }
}


@Composable
fun CardFrame(items: List<InfoItem>) {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Box(
            Modifier
                .width(300.dp)
                .height(540.dp)
                .padding(5.dp)
                .border(5.dp, PrimaryGradientBrush, RectangleShape)
                .padding(8.dp)
        ) {

            items.forEach { item ->
                if (item.content.isNotEmpty()) {
                    var offsetX by remember { mutableStateOf(item.offsetX) }
                    var offsetY by remember { mutableStateOf(item.offsetX) }

                    var (isSelected, setIsSelected) = remember { mutableStateOf(false) }

                    var (content, setContent) = remember { mutableStateOf(item.content) }
                    var (fontSize, setFontSize) = remember { mutableStateOf(item.fontSize) }
                    var (fontColor, setFontColor) = remember { mutableStateOf(item.fontColor) }
                    if (isSelected) {
                        FontTool(
                            setIsSelected,
                            content,
                            setContent,
                            fontSize,
                            setFontSize,
                            fontColor,
                            setFontColor
                        )
                    }
                    Box(Modifier.matchParentSize(), Alignment.Center) {
                        Text(
                            content,
                            Modifier
                                .offset {
                                    IntOffset(
                                        offsetX.roundToInt(),
                                        offsetY.roundToInt()
                                    )
                                }
                                .clickable {
                                    Log.i("${content}", "isSelected")
                                    setIsSelected(true)
                                }
                                .pointerInput(Unit) {
                                    detectDragGestures { change, dragAmount ->
                                        change.consumeAllChanges()
                                        offsetX += dragAmount.x
                                        offsetY += dragAmount.y
                                        Log.i("${content}", "$offsetX $offsetY")
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
        }
    }
}

@Composable
fun FontTool(
    setIsSelected: (Boolean) -> Unit,
    content: String,
    setContent: (String) -> Unit,
    fontSize: Int,
    setFontSize: (Int) -> Unit,
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


                var newFontSize by remember { mutableStateOf(fontSize.toFloat()) }
                var newFontColor by remember { mutableStateOf(fontColor) }

                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text(
                        content,
                        Modifier.sizeIn(20.dp),
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
                    // 폰트 사이즈 수정
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