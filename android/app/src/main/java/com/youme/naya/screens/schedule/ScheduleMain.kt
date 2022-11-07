package com.youme.naya.screens.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.youme.naya.widgets.calendar.AnimatedCalendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleMainScreen(navController: NavHostController) {
    Column {
        AnimatedCalendar(
            true
        )
    }
}