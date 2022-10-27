package com.youme.naya

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.youme.naya.ui.theme.AndroidTheme


private var mainContext: Context? = null

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainContext = applicationContext
        setContent {
            AndroidTheme {
                navController = rememberNavController()
                MainScreen(navController)
            }
        }
    }

}

fun getMainContext(): Context? {
    return mainContext?.applicationContext
}

@Preview(
    name = "naya Project",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MainPreview() {
//    MainScreen()
}