package com.youme.naya.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.youme.naya.AnimatedSplashScreen
import com.youme.naya.MainScreen
import com.youme.naya.SplashScreen
import com.youme.naya.login.LoginViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigationGraph(
    sharedImageUrl: String,
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = SplashScreen.Splash.route,
    ) {
        composable(route = SplashScreen.Splash.route) {
            AnimatedSplashScreen(navController, loginViewModel)
        }
        composable(route = Graph.BOTTOM) {
            MainScreen(sharedImageUrl)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val BOTTOM = "bottom_graph"
}
