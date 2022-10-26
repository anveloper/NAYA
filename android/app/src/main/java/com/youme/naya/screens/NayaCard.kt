package com.youme.naya.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun NayaCardScreen(
    navController: NavHostController
) {
    val (cameraOn, setCameraOn) = remember { mutableStateOf(false) }
    val (galleryOn, setGalleryOn) = remember { mutableStateOf(false) }
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
        if (cameraOn) {
            CameraScreen()
        } else if (galleryOn) {
            GalleryScreen() // 현재 실행 안됨
        } else {
            Text(
                text = "NAYA CARD",
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Button(onClick = {
//                navController.navigate(route = "camera")
                setCameraOn(true)
            }) {
                Text(text = "카메라", fontSize = 16.sp)
            }
            Button(onClick = {
                setGalleryOn(true)
            }) {
                Text(text = "갤러리", fontSize = 16.sp)
            }
        }

    }
}

@Preview
@Composable
fun NayaCardScreenPreview() {
//    NayaCardScreen()
}
