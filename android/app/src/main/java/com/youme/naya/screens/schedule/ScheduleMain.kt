package com.youme.naya.screens.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.widgets.calendar.AnimatedCalendar
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.R
import com.youme.naya.database.entity.Schedule
import com.youme.naya.schedule.component.ScheduleItem
import com.youme.naya.schedule.component.ScheduleNone
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.fonts

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleMainScreen(
    navController: NavHostController,
    viewModel: ScheduleMainViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()

    fun dateConvert(date: String) : String {
        return "${date.substring(5, 7)}월 ${date.substring(8, 10)}일 "
    }
    // TODO : scroll
       Column (
           horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedCalendar(
                expanded = true,
                takeMeToDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
                customCalendarEvents = viewModel.eventList.value
            )
           Spacer(modifier = Modifier.height(16.dp))

           Column(
               modifier = Modifier.width(300.dp),
               content = {
                   Row(modifier = Modifier.padding(vertical = 12.dp),
                       verticalAlignment = Alignment.CenterVertically) {
                       Text(
                           dateConvert(viewModel.selectedDate.value),
                           color = PrimaryDark,
                           fontFamily = fonts,
                           fontWeight = FontWeight.Bold,
                           fontSize = 16.sp,
                       )
                       Image(
                           painter = painterResource(id = R.drawable.icon_calendar),
                           "calendar",
                           modifier = Modifier
                               .width(24.dp)
                               .height(24.dp)
                       )
                   }
                   Spacer(modifier = Modifier.height(16.dp))
                   if (viewModel.schedules.value.isNotEmpty()) {
                       LazyColumn (
                           state = rememberLazyListState(),
                           modifier = Modifier.width(300.dp),
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           items(viewModel.schedules.value) { schedule ->
                               ScheduleItem(
                                   schedule = schedule,
                                   navController = navController,
                               )
                           }
                       }
                   } else {
                       ScheduleNone(
                           navController = navController,
                           selectedDate = dateConvert(viewModel.selectedDate.value))
                   }
               }
           )
       }
}

