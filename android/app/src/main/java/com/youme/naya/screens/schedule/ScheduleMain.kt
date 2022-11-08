package com.youme.naya.screens.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.youme.naya.schedule.main.ScheduleMainViewModel
import com.youme.naya.widgets.calendar.AnimatedCalendar
import com.youme.naya.widgets.calendar.CalendarViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.youme.naya.schedule.Screen
import com.youme.naya.schedule.main.components.ScheduleItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleMainScreen(
    navController: NavHostController,
    viewModel: ScheduleMainViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    var schedules = state.schedules

    val calendarViewModel = viewModel<CalendarViewModel>()
    var selectedDate by remember { mutableStateOf(calendarViewModel.selectedDate) }


       Column {
            AnimatedCalendar(
                expanded = true,
                takeMeToDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
            )
           LazyColumn {
               items(schedules) { schedule ->
                   ScheduleItem(
                       schedule = schedule,
                       onDetailSchedule = {
                           navController.navigate(
                               route = Screen.ScheduleDetail.passId(schedule.scheduleId))
                       }
                   )
               }
           }
        }
}

