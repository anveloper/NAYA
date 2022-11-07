package com.youme.naya.widgets.calendar.customCalendar.component.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.youme.naya.ui.theme.fonts
import com.youme.naya.widgets.calendar.customCalendar.component.text.config.CustomCalendarTextSize


@Composable
fun CustomCalendarNormalText(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight,
    textColor: Color,
    textSize: TextUnit = CustomCalendarTextSize.Normal.size

) {
    Text(
        modifier = modifier,
        color = textColor,
        fontSize = textSize,
        fontFamily = fonts,
        text = text,
        fontWeight = fontWeight,
        textAlign = TextAlign.Center
    )
}

@Composable
fun CustomCalendarSmallText(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight,
    textColor: Color,
    textSize: TextUnit = CustomCalendarTextSize.Small.size

) {
    Text(
        modifier = modifier,
        color = textColor,
        fontSize = textSize,
        fontFamily = fonts,
        text = text,
        fontWeight = fontWeight,
        textAlign = TextAlign.Center
    )
}