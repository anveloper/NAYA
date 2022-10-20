package com.youme.naya.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController

@Composable
fun NayaCardScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Blue,
                        Color.DarkGray
                    )
                ),
                alpha = 0.4f
            ),
    ) {
        Text(
            text = "NAYA CARD",
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Button(onClick = { navController.navigate("camera") }) {
            Text(text = "카메라")
        }
    }
}

//@Composable
//@Preview
//fun NayaCardScreenPreview() {
//    NayaCardScreen()
//}