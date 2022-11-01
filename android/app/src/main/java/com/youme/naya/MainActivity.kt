package com.youme.naya

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.youme.naya.camera.CameraX
import com.youme.naya.login.LoginActivity
import com.youme.naya.login.LoginViewModel
import com.youme.naya.ui.theme.AndroidTheme

class MainActivity : BaseActivity() {
    // Firebase
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 로그인 시도
        viewModel.tryLogin(this)

        lifecycleScope.launchWhenCreated {
            viewModel.loginResult.collect { isLogin ->
                if (!isLogin) {
                    // 로그인 안되어있을 때 로그인 페이지 열림
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                }
            }
        }
        setContent {
            Surface(color = Color.White) {
                Text(text = "로그인 확인중", fontSize = 30.sp)
            }
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