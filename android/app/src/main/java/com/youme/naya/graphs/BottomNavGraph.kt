package com.youme.naya.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.youme.naya.BottomBarScreen
import com.youme.naya.screens.*
import com.youme.naya.screens.schedule.ScheduleCreateScreen
import com.youme.naya.screens.schedule.ScheduleDetailScreen
import com.youme.naya.screens.schedule.ScheduleMainScreen
import com.youme.naya.screens.schedule.ScheduleUpdateScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.NuyaCardHolder.route) {
            NuyaCardHolderScreen(navController = navController)
        }
        composable(route = BottomBarScreen.NayaCard.route) {
            NayaCardScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Calendar.route) {
            ScheduleMainScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
        // camera
        composable(route = "camera") {
//            CameraScreen()
        }
        // Nuya 명함 생성 (직접 입력)
        composable(route = "bCardCreate") { entry ->
            BCardCreateScreen(navController = navController)
        }
        composable(route = "scheduleCreate") {
            ScheduleCreateScreen(navController = navController)
        }
        composable(route = "scheduleUpdate") {
            ScheduleUpdateScreen()
        }
        composable(route = "scheduleDetail") {
            ScheduleDetailScreen()
        }
    }
}