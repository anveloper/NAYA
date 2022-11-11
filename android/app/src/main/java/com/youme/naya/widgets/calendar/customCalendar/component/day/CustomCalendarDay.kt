package com.youme.naya.widgets.calendar.customCalendar.component.day.config

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.ui.theme.SecondaryLightBlue
import com.youme.naya.widgets.calendar.customCalendar.component.text.CustomCalendarNormalText
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarDay
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarEvent
import kotlinx.datetime.LocalDate

@Composable
fun CustomCalendarDay(
    customCalendarDay: CustomCalendarDay,
    selectedCustomCalendarDay: LocalDate,
    customCalendarDayColors: CustomCalendarDayColors,
    dotColor: Color,
    dayBackgroundColor: Color,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    textSize: TextUnit = 16.sp,
    customCalendarEvents: List<CustomCalendarEvent> = emptyList(),
    isCurrentDay: Boolean = false,
    onCurrentDayClick: (CustomCalendarDay, List<CustomCalendarEvent>) -> Unit = { _, _ -> },

    ) {
    val customCalendarDayState = getCustomCalendarDayState(selectedCustomCalendarDay, customCalendarDay.localDate)
    val bgColor = getBackgroundColor(customCalendarDayState, dayBackgroundColor)
    val textColor = getTextColor(customCalendarDayState, customCalendarDayColors, isCurrentDay)
    val weight = getTextWeight(customCalendarDayState)

    Column(
        modifier = modifier
            .clip(shape = CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) { onCurrentDayClick(customCalendarDay, customCalendarEvents) }
            .size(size = size)
            .background(color = bgColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CustomCalendarNormalText(
            text = customCalendarDay.localDate.dayOfMonth.toString(),
            modifier = Modifier,
            fontWeight = weight,
            textColor = textColor,
            textSize = textSize,
        )
        Row(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
        ) {
            val customCalendarEventForDay = customCalendarEvents.filter { it.date == customCalendarDay.localDate }
            if (customCalendarEventForDay.isNotEmpty()) {
                val dayEvents = if (customCalendarEventForDay.count() > 3) customCalendarEventForDay.take(3) else customCalendarEventForDay
                dayEvents.forEachIndexed { index, _ ->
                    CustomCalendarDots(
                        modifier = Modifier, index = index, size = size, color = Color(dayEvents[index].eventColor)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomCalendarDots(
    modifier: Modifier = Modifier,
    index: Int,
    size: Dp,
    color: Color
) {
    Box(
        modifier = modifier
            .padding(horizontal = 1.dp)
            .clip(shape = CircleShape)
            .background(
                color = color.copy(alpha = index.plus(1) * 0.3F)
            )
            .size(size = size.div(12))
    )
}

private fun getCustomCalendarDayState(selectedDate: LocalDate, currentDay: LocalDate) =
    when (selectedDate) {
        currentDay -> CustomCalendarDayState.CustomCalendarDaySelected
        else -> CustomCalendarDayState.CustomCalendarDayDefault
    }

private fun getTextWeight(customCalendarDayState: CustomCalendarDayState) =
    if (customCalendarDayState is CustomCalendarDayState.CustomCalendarDaySelected) {
        FontWeight.Bold
    } else {
        FontWeight.SemiBold
    }

private fun getBackgroundColor(
    customCalendarDayState: CustomCalendarDayState,
    backgroundColor: Color,
) = if (customCalendarDayState is CustomCalendarDayState.CustomCalendarDaySelected) {
    backgroundColor
} else {
    Color.Transparent
}

private fun getTextColor(
    customCalendarDayState: CustomCalendarDayState,
    customCalendarDayColors: CustomCalendarDayColors,
    isCurrentDay : Boolean,
): Color = if (customCalendarDayState is CustomCalendarDayState.CustomCalendarDaySelected) {
    customCalendarDayColors.selectedTextColor
} else {
    if (isCurrentDay) {
        SecondaryLightBlue
    } else {
        customCalendarDayColors.textColor
    }
}