package com.youme.naya.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.youme.naya.BottomBarScreen
import com.youme.naya.screens.*

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
            CalendarScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
        // camera
        composable(route = "camera") {
//            CameraScreen()
        }
        // Nuya 명함 생성 (직접 입력)
        composable(route = "bCardEdit") { entry ->
            BCardEditScreen(navController = navController)
        }
    }
}