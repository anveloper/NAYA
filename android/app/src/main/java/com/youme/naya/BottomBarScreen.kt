package com.youme.naya

sealed class BottomBarScreen(val route: String, val title: String, val icon: Int?) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.nav_home_icon
    )

    object NuyaCardHolder : BottomBarScreen(
        route = "nuya",
        title = "Nuya",
        icon = R.drawable.nav_nuya_icon
    )

    object Spacer : BottomBarScreen(
        route = "",
        title = "",
        icon = null
    )

    object NayaCard : BottomBarScreen(
        route = "naya",
        title = "Naya",
        icon = R.drawable.nav_naya_icon
    )

    object Calendar : BottomBarScreen(
        route = "schedule",
        title = "Schedule",
        icon = R.drawable.nav_schedule_icon
    )

    object Settings : BottomBarScreen(
        route = "settings",
        title = "Settings",
        icon = R.drawable.home_icon_setting
    )

}