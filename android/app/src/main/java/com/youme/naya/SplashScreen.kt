package com.youme.naya.splash

sealed class SplashScreen(val route : String) {
    object Splash : SplashScreen(
        route = "splash_screen"
    )
}
