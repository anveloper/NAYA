package com.youme.naya.screens.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.widgets.calendar.AnimatedCalendar
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.youme.naya.schedule.main.components.ScheduleItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleMainScreen(
    navController: NavHostController,
    viewModel: ScheduleMainViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    var selectedDate by remember { mutableStateOf(viewModel.selectedDate) }
    val schedules by remember { mutableStateOf(viewModel.schedules.value) }

       Column {
            AnimatedCalendar(
                expanded = true,
                takeMeToDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
            )
           LazyColumn {
               items(schedules) { schedule ->
                   ScheduleItem(
                       schedule = schedule,
                       selectedDate = selectedDate.value,
                       onDetailSchedule = {
                           viewModel.onScheduleClick(schedule)
                       },
                   )
               }
           }
        }
}

