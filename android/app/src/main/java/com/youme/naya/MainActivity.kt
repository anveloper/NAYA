package com.youme.naya

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.youme.naya.ui.theme.AndroidTheme


class MainActivity : BaseActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTheme {
                navController = rememberNavController()
                MainScreen(navController)
            }
        }
    }
}

@Preview(
    name = "naya Project",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MainPreview() {
    MainScreen(rememberNavController())
}