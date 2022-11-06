package com.youme.naya.widgets.calendar.customCalendar.component.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.widgets.calendar.customCalendar.component.text.config.CustomCalendarTextConfig
import com.youme.naya.widgets.calendar.customCalendar.component.text.config.CustomCalendarTextDefaults

@Composable
fun CustomCalendarTitle(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.SemiBold,
    CustomCalendarTextConfig: CustomCalendarTextConfig = CustomCalendarTextDefaults.customCalendarTitleTextConfig()
) {
    Text(
        modifier = modifier,
        color = PrimaryDark,
        fontSize = CustomCalendarTextConfig.customCalendarTextSize.size,
        text = text,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}
