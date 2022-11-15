package com.youme.naya.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.youme.naya.BottomBarScreen
import com.youme.naya.card.BusinessCardModifyScreen
import com.youme.naya.database.entity.Card
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
            NuyaCardScreen(navController = navController)
        }
        composable(route = BottomBarScreen.NayaCard.route) {
            NayaCardScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Calendar.route) {
            ScheduleMainScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        // Nuya 명함 생성 (직접 입력)
        composable(route = "bCardCreate") { entry ->
            BCardCreateScreen(navController = navController)
        }
        // Nuya 명함 생성 (카메라 촬영)
        composable(route = "bCardCreateByCamera?result={result}&path={path}", arguments = listOf(
            navArgument("result") {
                type = NavType.StringType
            },
            navArgument("path") {
                type = NavType.StringType
            }
        )) {
            val result = it.arguments?.getString("result")!!
            val path = it.arguments?.getString("path")!!
            BCardCreateByCameraScreen(navController = navController, result = result, path = path)
        }
        composable(route = "bCardModify?card={card}", arguments = listOf(
            navArgument("card") {
                type = NavType.StringType
            }
        )) {
            val cardJson = it.arguments?.getString("card")
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(Card::class.java).lenient()
            val cardObject = jsonAdapter.fromJson(cardJson)!!

            BusinessCardModifyScreen(navController = navController, cardObject)
        }
        composable(route = "scheduleCreate") {
            ScheduleCreateScreen(navController = navController)
        }
        composable(route = "scheduleDetail/{scheduleId}",
        arguments = listOf(
            navArgument(
            name = "scheduleId"
        ) {
            type = NavType.IntType
            defaultValue = -1
        },))
            {
            val scheduleId = it.arguments?.getInt("scheduleId") ?: -1
            ScheduleDetailScreen(
                navController = navController,
                scheduleId = scheduleId
            )
        }
        composable(route = "scheduleEdit/{scheduleId}",
            arguments = listOf(
                navArgument(
                    name = "scheduleId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },))
        {
            val scheduleId = it.arguments?.getInt("scheduleId") ?: -1
            ScheduleUpdateScreen(
                navController = navController,
                scheduleId = scheduleId
            )
        }
    }
}