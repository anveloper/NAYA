package com.youme.naya.graphs

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.youme.naya.AnimatedSplashScreen
import com.youme.naya.MainScreen
import com.youme.naya.SplashScreen
import com.youme.naya.intro.IntroViewModel
import com.youme.naya.login.LoginViewModel
import com.youme.naya.login.PermissionViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigationGraph(
    sharedImageUrl: String,
    checkPermission: () -> Boolean,
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    introViewModel: IntroViewModel,
    permissionViewModel: PermissionViewModel
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = SplashScreen.Splash.route,
    ) {
        composable(route = SplashScreen.Splash.route) {
            AnimatedSplashScreen(navController, loginViewModel)
        }
        composable(route = Graph.BOTTOM) {
            if (!checkPermission()) {
                Toast.makeText(context, "사용자에 의해 일부 권한이 수동으로 거부되었습니다.", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "정상적인 이용이 제한될 수 있습니다.", Toast.LENGTH_SHORT).show()
            }
            MainScreen(sharedImageUrl, introViewModel, permissionViewModel)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val BOTTOM = "bottom_graph"
}
