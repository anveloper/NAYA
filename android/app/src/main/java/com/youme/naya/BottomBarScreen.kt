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
        icon = Icons.Default.Person
    )

    object NayaCard : BottomBarScreen(
        route = "naya",
        title = "Naya",
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