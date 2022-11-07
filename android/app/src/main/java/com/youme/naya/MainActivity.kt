package com.youme.naya

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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

    private var permissionList = listOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.NFC,
    )

    @RequiresApi(Build.VERSION_CODES.O)
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

        // main에 달아놨으나, 스플레시나 회원가입 쪽으로 이동해야 할 것 같습니다.
        checkPermission()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        for (permission in permissionList) {
            if (checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permissionList.toTypedArray(), 0)
            }
            Log.i("Permission Check", "processing")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "naya Project",
    showBackground = true,
    showSystemUi = true
)

@Composable
fun MainPreview() {
    RootNavigationGraph(rememberNavController())
}