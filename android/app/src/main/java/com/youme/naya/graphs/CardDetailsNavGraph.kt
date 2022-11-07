package com.youme.naya.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.youme.naya.CardDetailsScreen
import com.youme.naya.card.BusinessCardModifyScreen
import com.youme.naya.card.CardDetailsMainScreen
import com.youme.naya.database.entity.Card

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
            CardDetailsMainScreen(navController = navController, cardId)
        }
        composable(
            route = CardDetailsScreen.BCardModify.route
        ) {
            val cardJson = it.arguments?.getString("card")
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(Card::class.java).lenient()
            val cardObject = jsonAdapter.fromJson(cardJson)!!

            BusinessCardModifyScreen(navController = navController, cardObject)
        }
    }
}