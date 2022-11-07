package com.youme.naya.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.youme.naya.BottomBarScreen
import com.youme.naya.CardDetailsScreen
import com.youme.naya.card.CardDetailsMainScreen
import com.youme.naya.card.CardModifyScreen
import com.youme.naya.screens.*

@Composable
fun CardDetailsNavGraph(
    navController: NavHostController,
    cardId: Int
) {
    NavHost(
        navController = navController,
        startDestination = CardDetailsScreen.Details.route
    ) {
        composable(route = CardDetailsScreen.Details.route) {
            CardDetailsMainScreen(cardId)
        }
        composable(route = CardDetailsScreen.Modify.route) {
            CardModifyScreen()
        }
    }
}