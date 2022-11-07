package com.youme.naya.widgets.calendar.customCalendar.ui.basic

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.youme.naya.ui.theme.*
import com.youme.naya.widgets.calendar.customCalendar.component.day.config.CustomCalendarDayColors
import com.youme.naya.widgets.calendar.customCalendar.component.header.CustomCalendarHeader
import com.youme.naya.widgets.calendar.customCalendar.component.header.config.CustomCalendarHeaderConfig
import com.youme.naya.widgets.calendar.customCalendar.component.text.config.CustomCalendarTextConfig
import com.youme.naya.widgets.calendar.customCalendar.component.text.config.CustomCalendarTextSize
import com.youme.naya.widgets.calendar.customCalendar.component.day.config.CustomCalendarDay
import com.youme.naya.widgets.calendar.customCalendar.component.text.CustomCalendarSmallText
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarDay
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarEvent
import com.youme.naya.widgets.calendar.customCalendar.model.toCustomCalendarDay
import com.youme.naya.widgets.calendar.customCalendar.ui.WeekDays
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.todayIn

//val WeekDays = listOf("월", "화", "수", "목", "금", "토", "일")

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalendarBasic(
    modifier: Modifier = Modifier,
    takeMeToDate: LocalDate?,
    customCalendarDayColors: CustomCalendarDayColors,
    customCalendarHeaderConfig: CustomCalendarHeaderConfig? = null,
    customCalendarThemeColors: List<CustomCalendarThemeColor>,
    customCalendarEvents: List<CustomCalendarEvent> = emptyList(),
    onCurrentDayClick: (CustomCalendarDay, List<CustomCalendarEvent>) -> Unit = { _, _ -> },
) {
    val currentDay = takeMeToDate ?: Clock.System.todayIn(TimeZone.currentSystemDefault())
    val displayedMonth = remember {
        mutableStateOf(currentDay.month)
    }
    val displayedYear = remember {
        mutableStateOf(currentDay.year)
    }
    val currentMonth = displayedMonth.value
    val currentYear = displayedYear.value

    val daysInMonth = currentMonth.minLength()
    val monthValue: String =
        if (currentMonth.value.toString().length == 1) "0" + currentMonth.value.toString() else currentMonth.value.toString()
    val startDayOfMonth = "$currentYear-$monthValue-01".toLocalDate()
    val firstDayOfMonth = startDayOfMonth.dayOfWeek
    val selectedCustomCalendarDate = remember { mutableStateOf(currentDay) }
    val newCustomCalendarHeaderConfig = CustomCalendarHeaderConfig(
        customCalendarTextConfig = CustomCalendarTextConfig(
            customCalendarTextSize = CustomCalendarTextSize.SubTitle,
        )
    )
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ){
        CustomCalendarHeader(
            modifier = Modifier.padding(bottom = 16.dp),
            month = displayedMonth.value,
            onPreviousClick = {
                if (displayedMonth.value.value == 1) {
                    displayedYear.value = displayedYear.value.minus(1)
                }
                displayedMonth.value = displayedMonth.value.minus(1)
            },
            onNextClick = {
                if (displayedMonth.value.value == 12) {
                    displayedYear.value = displayedYear.value.plus(1)
                }
                displayedMonth.value = displayedMonth.value.plus(1)
            },
            year = displayedYear.value,
            customCalendarHeaderConfig = customCalendarHeaderConfig ?: newCustomCalendarHeaderConfig
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth(),
            ) {
            items(WeekDays) {
                CustomCalendarSmallText(
                    modifier = Modifier.height(28.dp),
                    text = it,
                    fontWeight = FontWeight.Medium,
                    textColor = NeutralLight,
                )
            }
            items((getInitialDayOfMonth(firstDayOfMonth)..daysInMonth).toList()) {
                if (it > 0) {
                    val day = getGeneratedDay(it, currentMonth, currentYear)
                    val isCurrentDay = day == currentDay
                    CustomCalendarDay(
                        customCalendarDay = day.toCustomCalendarDay(),
                        modifier = Modifier,
                        customCalendarEvents = customCalendarEvents,
                        isCurrentDay = isCurrentDay,
                        onCurrentDayClick = { customCalendarDay, events ->
                            selectedCustomCalendarDate.value = customCalendarDay.localDate
                            onDateChanged(selectedCustomCalendarDate.value)
                            onCurrentDayClick(customCalendarDay, events)
                        },
                        selectedCustomCalendarDay = selectedCustomCalendarDate.value,
                        customCalendarDayColors = customCalendarDayColors,
                        dotColor = PrimaryBlue,
                        dayBackgroundColor = customCalendarThemeColors[currentMonth.value.minus(1)].dayBackgroundColor,
                    )
                }
            }
        }
    }
}

fun onDateChanged(value: Any) {

}

@RequiresApi(Build.VERSION_CODES.O)
private fun getInitialDayOfMonth(firstDayOfMonth: DayOfWeek) = -(firstDayOfMonth.value).minus(2)

@RequiresApi(Build.VERSION_CODES.O)
private fun getGeneratedDay(day: Int, currentMonth: Month, currentYear: Int): LocalDate {
    val monthValue =
        if (currentMonth.value.toString().length == 1) "0${currentMonth.value}" else currentMonth.value.toString()
    val newDay = if (day.toString().length == 1) "0$day" else day
    return "$currentYear-$monthValue-$newDay".toLocalDate()
}



internal object CustomCalendarColors {

    fun defaultColors(): List<CustomCalendarThemeColor> = buildList {
        repeat(12) {
            add(
                CustomCalendarThemeColor(
                    NeutralWhite, 
                    PrimaryBlue,
                    NeutralWhite
                )
            )
        }
    }
}

data class CustomCalendarThemeColor(
    val backgroundColor: Color,
    val dayBackgroundColor: Color,
    val headerTextColor: Color,
)