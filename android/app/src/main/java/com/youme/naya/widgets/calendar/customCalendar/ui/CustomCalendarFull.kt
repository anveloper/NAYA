package com.youme.naya.widgets.calendar.customCalendar.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.youme.naya.ui.theme.NeutralLight
import com.youme.naya.ui.theme.PrimaryBlue
import com.youme.naya.widgets.calendar.CalendarViewModel
import com.youme.naya.widgets.calendar.customCalendar.component.day.config.CustomCalendarDay
import com.youme.naya.widgets.calendar.customCalendar.component.day.config.CustomCalendarDayColors
import com.youme.naya.widgets.calendar.customCalendar.component.header.CustomCalendarHeader
import com.youme.naya.widgets.calendar.customCalendar.component.header.config.CustomCalendarHeaderConfig
import com.youme.naya.widgets.calendar.customCalendar.component.text.CustomCalendarSmallText
import com.youme.naya.widgets.calendar.customCalendar.component.text.config.CustomCalendarTextConfig
import com.youme.naya.widgets.calendar.customCalendar.component.text.config.CustomCalendarTextSize
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarDay
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarEvent
import com.youme.naya.widgets.calendar.customCalendar.model.CustomCalendarType
import com.youme.naya.widgets.calendar.customCalendar.model.toCustomCalendarDay
import com.youme.naya.widgets.calendar.customCalendar.ui.basic.CustomCalendarThemeColor
import com.youme.naya.widgets.calendar.customCalendar.ui.basic.onDateChanged
import com.youme.naya.widgets.calendar.customCalendar.ui.fold.data.getNext7Dates
import com.youme.naya.widgets.calendar.customCalendar.ui.fold.data.getPrevious7Dates
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.*

val WeekDays: List<String>
    get() = listOf("월", "화", "수", "목", "금", "토", "일")

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalendarFull(
    customCalendarType: CustomCalendarType = CustomCalendarType.Fold(true),
    customCalendarDayColors: CustomCalendarDayColors,
    takeMeToDate: LocalDate,
    customCalendarThemeColors: List<CustomCalendarThemeColor>,
    modifier: Modifier = Modifier,
    customCalendarHeaderConfig: CustomCalendarHeaderConfig? = null,
    customCalendarEvents: List<CustomCalendarEvent> = emptyList(),
    onCurrentDayClick: (CustomCalendarDay, List<CustomCalendarEvent>) -> Unit = { _, _ -> },
) {
    val selectedCustomCalendarDate = remember { mutableStateOf(takeMeToDate) }
    val displayedYear = remember {
        mutableStateOf(selectedCustomCalendarDate.value.year)
    }
    val displayedMonth = remember {
        mutableStateOf(selectedCustomCalendarDate.value.month)
    }

    val currentMonth = displayedMonth.value
    val currentYear = displayedYear.value

    val daysInMonth = currentMonth.minLength()

    val monthValue: String =
        if (currentMonth.value.toString().length == 1) "0" + currentMonth.value.toString()
        else currentMonth.value.toString()

    val startDayOfMonth = "$currentYear-$monthValue-01".toLocalDate()

    val firstDayOfMonth = startDayOfMonth.dayOfWeek
    val newCustomCalendarHeaderConfig = CustomCalendarHeaderConfig(
        customCalendarTextConfig = CustomCalendarTextConfig(
            customCalendarTextSize = CustomCalendarTextSize.SubTitle,
        )
    )

    val weekValue = remember { mutableStateOf(selectedCustomCalendarDate.value.getNext7Dates()) }
    val month = weekValue.value.last().month
    val year = weekValue.value.last().year

    val calendarViewModel = viewModel<CalendarViewModel>()

    val selectedDate = remember {
        mutableStateOf(calendarViewModel.selectedDate)
    }

    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
         when (customCalendarType){
            // 접힐 때 계산
            is CustomCalendarType.Fold -> {
                CustomCalendarHeader(
                modifier = Modifier.padding(bottom = 12.dp),
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
            )}
             // 펴질 때
            CustomCalendarType.Basic -> {CustomCalendarHeader(
                modifier = Modifier.padding(bottom = 12.dp),
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
            )}

        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth(),
            content = {
                items(WeekDays) {
                    CustomCalendarSmallText(
                        modifier = Modifier.height(28.dp),
                        text = it,
                        fontWeight = FontWeight.Medium,
                        textColor = NeutralLight,
                    )
                }
                when (customCalendarType){
                    is CustomCalendarType.Fold -> {
                items(weekValue.value) { localDate ->
                    val isCurrentDay = localDate == selectedCustomCalendarDate.value
                    CustomCalendarDay(
                        modifier = Modifier,
                        isCurrentDay = isCurrentDay,
                        customCalendarDay = localDate.toCustomCalendarDay(),
                        customCalendarEvents = customCalendarEvents.filter { it.date.dayOfMonth == localDate.dayOfMonth },
                        onCurrentDayClick = { customCalendarDay, events ->
                            selectedCustomCalendarDate.value = customCalendarDay.localDate
                            weekValue.value = selectedCustomCalendarDate.value.getNext7Dates()
                            calendarViewModel.getSelectedDate(selectedCustomCalendarDate.value)
                            onCurrentDayClick(customCalendarDay, events)
                        },
                        customCalendarDayColors = customCalendarDayColors,
                        selectedCustomCalendarDay = selectedCustomCalendarDate.value,
                        dotColor = PrimaryBlue,
                        dayBackgroundColor = customCalendarThemeColors[month.value.minus(1)].dayBackgroundColor
                        )
                    }
                }
                    CustomCalendarType.Basic -> {
                    items((getInitialDayOfMonth(firstDayOfMonth)..daysInMonth).toList()) {
                        if (it > 0) {
                            val day = getGeneratedDay(it, currentMonth, currentYear)
                            val isCurrentDay = day == selectedCustomCalendarDate.value
                            CustomCalendarDay(
                                customCalendarDay = day.toCustomCalendarDay(),
                                modifier = Modifier,
                                customCalendarEvents = customCalendarEvents,
                                isCurrentDay = isCurrentDay,
                                onCurrentDayClick = { customCalendarDay, events ->
                                    selectedCustomCalendarDate.value = customCalendarDay.localDate
                                    onDateChanged(selectedCustomCalendarDate.value)
                                    weekValue.value = selectedCustomCalendarDate.value.getNext7Dates()
                                    calendarViewModel.getSelectedDate(selectedCustomCalendarDate.value)
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
        )
    }
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
