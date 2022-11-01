package com.youme.naya.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.youme.naya.AnimatedSplashScreen
import com.youme.naya.MainScreen
import com.youme.naya.SplashScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = SplashScreen.Splash.route,
    ) {
        composable(route = SplashScreen.Splash.route) {
            AnimatedSplashScreen(navController)
        }
        composable(route = Graph.Bottom) {
            MainScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val Bottom = "bottom_graph"
}
