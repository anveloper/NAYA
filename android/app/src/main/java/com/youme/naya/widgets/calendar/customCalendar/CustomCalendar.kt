package com.youme.naya.widgets.calendar.customCalendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.youme.naya.widgets.calendar.customCalendar.component.day.config.CustomCalendarDayColors
import com.youme.naya.widgets.calendar.customCalendar.component.day.config.CustomCalendarDayDefaultColors
import com.youme.naya.widgets.calendar.customCalendar.component.header.config.CustomCalendarHeaderConfig
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarDay
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarEvent
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarType
import com.youme.naya.widgets.calendar.customCalendar.ui.CustomCalendarFull
import com.youme.naya.widgets.calendar.customCalendar.ui.basic.CustomCalendarBasic
import com.youme.naya.widgets.calendar.customCalendar.ui.basic.CustomCalendarColors
import com.youme.naya.widgets.calendar.customCalendar.ui.basic.CustomCalendarThemeColor
import com.youme.naya.widgets.calendar.customCalendar.ui.fold.CustomCalendarFold
import kotlinx.datetime.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalendar(
    modifier: Modifier = Modifier,
    takeMeToDate: LocalDate,
    customCalendarType: CustomCalendarType = CustomCalendarType.Fold(true),
    customCalendarEvents: List<CustomCalendarEvent> = emptyList(),
    customCalendarThemeColors: List<CustomCalendarThemeColor> = CustomCalendarColors.defaultColors(),
    onCurrentDayClick: (CustomCalendarDay, List<CustomCalendarEvent>) -> Unit = { _, _ -> },
    customCalendarDayColors: CustomCalendarDayColors = CustomCalendarDayDefaultColors.defaultColors(),
    customCalendarHeaderConfig: CustomCalendarHeaderConfig? = null,
) {
    CustomCalendarFull(
        modifier = modifier.wrapContentHeight(),
        customCalendarEvents = customCalendarEvents,
        onCurrentDayClick = onCurrentDayClick,
        customCalendarDayColors = customCalendarDayColors,
        customCalendarThemeColors = customCalendarThemeColors,
        customCalendarHeaderConfig = customCalendarHeaderConfig,
        customCalendarType = customCalendarType,
        takeMeToDate = takeMeToDate,
    )}