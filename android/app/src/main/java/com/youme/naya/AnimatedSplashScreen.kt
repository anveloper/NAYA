package com.youme.naya

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.youme.naya.graphs.Graph
import com.youme.naya.login.LoginActivity
import com.youme.naya.login.LoginViewModel
import com.youme.naya.network.RetrofitClient
import com.youme.naya.network.RetrofitService
import com.youme.naya.ui.theme.SecondaryGradientBrush
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavHostController, loginViewModel: LoginViewModel) {
    // 애니메이션
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    val retrofit = RetrofitClient.getInstance()
    val supplementService = retrofit.create(RetrofitService::class.java)

    val context = LocalContext.current
    val activity = context as? Activity
    val lifecycleOwner = LocalLifecycleOwner.current

    lifecycleOwner.lifecycleScope.launchWhenCreated {
        loginViewModel.loginResult.collect { isLogin ->
            if (!isLogin) {
                // 로그인 안되어있을 때 로그인 페이지 열림
                context.startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
    }

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        // 뒤로 가기 버튼 누르면 바깥으로 나가게
        navController.popBackStack()
        navController.navigate(Graph.BOTTOM)
    }
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .background(SecondaryGradientBrush)
            .fillMaxSize(),
        // 로고 가운데 배치
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.splash_logo_text_light),
            contentDescription = "logo",
            modifier = Modifier
                .size(150.dp)
                .alpha(alpha = alpha)
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    Splash(alpha = 1f)
}