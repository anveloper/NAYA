package com.youme.naya

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object NuyaCardHolder : BottomBarScreen(
        route = "nuya",
        title = "Nuya",
<<<<<<< HEAD
        icon = Icons.Default.Person
=======
        icon = R.drawable.nav_nuya_icon
    )

    object Spacer : BottomBarScreen(
        route = "",
        title = "",
        icon = null
>>>>>>> android
    )

    object NayaCard : BottomBarScreen(
        route = "naya",
        title = "naya",
        icon = Icons.Default.Star
    )

    object Calendar : BottomBarScreen(
        route = "calendar",
        title = "Calendar",
        icon = Icons.Default.Check
    )

    object Settings : BottomBarScreen(
        route = "settings",
        title = "Settings",
        icon = Icons.Default.Settings
    )

}