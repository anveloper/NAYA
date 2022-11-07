package com.youme.naya.widgets.calendar.customCalendar.ui.fold

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.youme.naya.ui.theme.NeutralLight
import com.youme.naya.ui.theme.PrimaryBlue
import com.youme.naya.widgets.calendar.customCalendar.component.day.config.CustomCalendarDayColors
import com.youme.naya.widgets.calendar.customCalendar.component.header.CustomCalendarHeader
import com.youme.naya.widgets.calendar.customCalendar.component.header.config.CustomCalendarHeaderConfig
import com.youme.naya.widgets.calendar.customCalendar.component.text.config.CustomCalendarTextConfig
import com.youme.naya.widgets.calendar.customCalendar.component.text.config.CustomCalendarTextSize
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarDay
import com.youme.naya.widgets.calendar.customCalendar.component.day.config.CustomCalendarDay
import com.youme.naya.widgets.calendar.customCalendar.component.text.CustomCalendarSmallText
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarEvent
import com.youme.naya.widgets.calendar.customCalendar.model.toCustomCalendarDay
import com.youme.naya.widgets.calendar.customCalendar.ui.WeekDays
import com.youme.naya.widgets.calendar.customCalendar.ui.basic.CustomCalendarThemeColor
import com.youme.naya.widgets.calendar.customCalendar.ui.fold.data.getNext7Dates
import com.youme.naya.widgets.calendar.customCalendar.ui.fold.data.getPrevious7Dates
import kotlinx.datetime.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalendarFold(
    takeMeToDate: LocalDate?,
    customCalendarDayColors: CustomCalendarDayColors,
    customCalendarThemeColors: List<CustomCalendarThemeColor>,
    modifier: Modifier = Modifier,
    customCalendarHeaderConfig: CustomCalendarHeaderConfig? = null,
    customCalendarEvents: List<CustomCalendarEvent> = emptyList(),
    onCurrentDayClick: (CustomCalendarDay, List<CustomCalendarEvent>) -> Unit = { _, _ -> },
) {
    val currentDay = takeMeToDate ?: Clock.System.todayIn(TimeZone.currentSystemDefault())
    val selectedCustomCalendarDate = remember { mutableStateOf(currentDay) }
    val weekValue = remember { mutableStateOf(currentDay.getNext7Dates()) }

    val month = weekValue.value.last().month
    val year = weekValue.value.last().year

    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        CustomCalendarHeader(
            modifier = Modifier.padding(bottom = 16.dp),
            month = month,
            year = year,
            onPreviousClick = {
                val firstDayOfDisplayedWeek = weekValue.value.first()
                weekValue.value = firstDayOfDisplayedWeek.getPrevious7Dates()
            },
            onNextClick = {
                val lastDayOfDisplayedWeek = weekValue.value.last().plus(1, DateTimeUnit.DAY)
                weekValue.value = lastDayOfDisplayedWeek.getNext7Dates()
            },
            customCalendarHeaderConfig = customCalendarHeaderConfig ?: CustomCalendarHeaderConfig(
                customCalendarTextConfig = CustomCalendarTextConfig(
                    customCalendarTextSize = CustomCalendarTextSize.SubTitle
                )
            ),
        )

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(7),
            content = {
                items(WeekDays) {
                    CustomCalendarSmallText(
                        modifier = Modifier.height(28.dp),
                        text = it,
                        fontWeight = FontWeight.Medium,
                        textColor = NeutralLight,
                    )
                }
                items(weekValue.value) { localDate ->
                    val isCurrentDay = localDate == currentDay
                    CustomCalendarDay(
                        modifier = Modifier,
                        isCurrentDay = isCurrentDay,
                        customCalendarDay = localDate.toCustomCalendarDay(),
                        customCalendarEvents = customCalendarEvents.filter { it.date.dayOfMonth == localDate.dayOfMonth },
                        onCurrentDayClick = { customCalendarDay, events ->
                            selectedCustomCalendarDate.value = customCalendarDay.localDate
                            onCurrentDayClick(customCalendarDay, events)
                        },
                        customCalendarDayColors = customCalendarDayColors,
                        selectedCustomCalendarDay = selectedCustomCalendarDate.value,
                        dotColor = PrimaryBlue,
                        dayBackgroundColor = customCalendarThemeColors[month.value.minus(1)].dayBackgroundColor
                    )
                }
            }
        )
    }
}