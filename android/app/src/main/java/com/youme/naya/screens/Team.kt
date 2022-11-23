package com.youme.naya.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.youme.naya.R

@Composable
fun TeamScreen(
    navController: NavHostController) {
    Image(painterResource(R.drawable.team_image), null, Modifier.fillMaxSize())

}