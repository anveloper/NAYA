package com.youme.naya.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
            HomeScreen(navController = navController)
        }
        composable(route = BottomBarScreen.NuyaCardHolder.route) {
            NuyaCardHolderScreen(navController = navController)
        }
        composable(route = BottomBarScreen.NayaCard.route) {
            NayaCardScreen()
        }
        composable(route = BottomBarScreen.Calendar.route) {
            ScheduleMainScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
        // Nuya 명함 생성 (직접 입력)
        composable(route = "bCardCreate") { entry ->
            BCardCreateScreen(navController = navController)
        }
        // Nuya 명함 생성 (카메라 촬영)
        composable(route = "bCardCreateByCamera?result={result}", arguments = listOf(
            navArgument("result") {
                type = NavType.StringType
            }
        )) {
            val result = it.arguments?.getString("result")!!
            BCardCreateByCameraScreen(navController = navController, result = result)
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