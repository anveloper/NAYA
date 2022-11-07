package com.youme.naya.widgets.calendar.customCalendar.component.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.fonts
import com.youme.naya.ui.theme.pico

import com.youme.naya.widgets.calendar.customCalendar.component.text.config.CustomCalendarTextConfig
import com.youme.naya.widgets.calendar.customCalendar.component.text.config.CustomCalendarTextDefaults

@Composable
fun CustomCalendarSubTitle(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    customCalendarTextConfig: CustomCalendarTextConfig = CustomCalendarTextDefaults.customCalendarTitleTextConfig()
) {
    Text(
        text = text,
        modifier = modifier,
        color = PrimaryDark,
        fontSize = customCalendarTextConfig.customCalendarTextSize.size,
        fontFamily = pico,
        textAlign = textAlign
    )
}