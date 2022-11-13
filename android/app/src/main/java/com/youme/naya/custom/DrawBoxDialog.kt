package com.youme.naya.custom

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import com.youme.naya.ui.theme.NeutralLightness
import com.youme.naya.ui.theme.PrimaryBlue
import com.youme.naya.ui.theme.PrimaryLight
import com.youme.naya.ui.theme.fonts
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.rememberDrawController


@Composable
fun DrawBoxDialog(
    setNewDraw: () -> Unit,
    addDraw: (ImageBitmap) -> Unit
) {
    var strokeColor by remember { mutableStateOf(PrimaryBlue) }
    var strokeWidth by remember { mutableStateOf(16f) }
    val controller = rememberDrawController()
    val captureController = rememberCaptureController()
    controller.setStrokeColor(strokeColor)
    controller.setStrokeWidth(strokeWidth)

    AlertDialog(onDismissRequest = { setNewDraw() },
        buttons = {
            Column(
                Modifier
                    .width(250.dp)
                    .height(450.dp)
                    .background(NeutralLightness), Arrangement.Center, Alignment.CenterHorizontally
            ) {
                Row(Modifier.fillMaxWidth(), Arrangement.End, Alignment.CenterVertically) {
                    TextButton(
                        onClick = {
                            captureController.capture()
                            setNewDraw()
                        }) {
                        Text(text = "완료", fontFamily = fonts, color = PrimaryBlue)
                    }
                }
                Capturable(controller = captureController, onCaptured = { bitmap, error ->
                    if (bitmap != null) {
                        if (bitmap.width > 1024) {
                            addDraw(resizeBitmap(1024, bitmap.asAndroidBitmap()).asImageBitmap())
                        } else {
                            addDraw(bitmap)
                        }
                    }
                    if (error != null) {
                    }
                }) {
                    DrawBox(
                        drawController = controller,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .weight(1f, true)
                    )
                }
                val (newWidth, setNewWidth) = remember { mutableStateOf(strokeWidth) }
                Row(
                    Modifier.fillMaxWidth(0.8f),
                    Arrangement.Center,
                    Alignment.CenterVertically
                ) {
                    Text(text = "두께")
                    Slider(
                        value = newWidth,
                        onValueChange = {
                            setNewWidth(it)
                        },
                        onValueChangeFinished = {
                            strokeWidth = newWidth
                        },
                        valueRange = 8f..24f,
                        steps = 160,
                        colors = SliderDefaults.colors(
                            thumbColor = PrimaryLight,
                            activeTrackColor = Color.Transparent
                        )
                    )
                }
                ClassicColorPicker(
                    color = strokeColor,
                    onColorChanged = { color: HsvColor ->
                        strokeColor = color.toColor()
//                        setFontColor(color.toColor())
                        Log.i("color", color.toColor().toString())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
            }
        }
    )
}

fun resizeBitmap(targetWidth: Int, source: Bitmap): Bitmap {
    val ratio = targetWidth.toDouble() / source.width.toDouble()
    val targetHeight = (source.height * ratio).toInt()
    return Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false)
}