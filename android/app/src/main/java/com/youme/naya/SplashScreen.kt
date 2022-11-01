package com.youme.naya

sealed class SplashScreen(val route : String) {
    object Splash : SplashScreen(
        route = "splash_screen"
    )
}
