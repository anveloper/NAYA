package com.youme.naya

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.youme.naya.graphs.RootNavigationGraph
import com.youme.naya.login.LoginActivity
import com.youme.naya.login.LoginViewModel
import com.youme.naya.ui.theme.AndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(TransitionMode.NONE) {
    // login viewModel
    private val viewModel by viewModels<LoginViewModel>()

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
            AndroidTheme {
                RootNavigationGraph(navController = rememberNavController())
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
    RootNavigationGraph(rememberNavController())
}