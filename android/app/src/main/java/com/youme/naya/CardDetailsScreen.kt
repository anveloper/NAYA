package com.youme.naya

sealed class CardDetailsScreen(val route: String, val title: String, val icon: Int?) {

    object Details : CardDetailsScreen(
        route = "details",
        title = "Details",
        icon = null
    )

    object BCardModify : CardDetailsScreen(
        route = "bCardModify?card={card}",
        title = "BCardModify",
        icon = null
    )

}