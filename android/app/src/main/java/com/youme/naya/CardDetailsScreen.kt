package com.youme.naya

sealed class CardDetailsScreen(val route: String, val title: String, val icon: Int?) {

    object Details : CardDetailsScreen(
        route = "details",
        title = "Details",
        icon = null
    )

    object Modify : CardDetailsScreen(
        route = "modify",
        title = "Modify",
        icon = null
    )

}