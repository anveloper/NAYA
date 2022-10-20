package com.youme.naya

import android.net.Uri
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.youme.naya.screens.*
import com.youme.naya.view.CameraView
import java.io.File
import java.util.concurrent.Executor

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.NuyaCardHolder.route) {
            NuyaCardHolderScreen()
        }
        composable(route = BottomBarScreen.NayaCard.route) {
            NayaCardScreen(navController)
        }
        composable(route = BottomBarScreen.Calendar.route) {
            CalendarScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
        composable(route = "camera") {
            CameraView(
                outputDirectory = outputDirectory,
                executor = executor,
                onImageCaptured = onImageCaptured,
                onError = onError
            )
        }
    }
}