package com.youme.naya

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.navigation.compose.rememberNavController
import com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE
import com.youme.naya.graphs.RootNavigationGraph
import com.youme.naya.intro.IntroViewModel
import com.youme.naya.login.LoginViewModel
import com.youme.naya.login.PermissionViewModel
import com.youme.naya.network.RetrofitClient
import com.youme.naya.network.RetrofitService
import com.youme.naya.ui.theme.AndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Retrofit

@Keep
@AndroidEntryPoint
class MainActivity : BaseActivity(TransitionMode.NONE) {
    // login viewModel
    private val loginViewModel by viewModels<LoginViewModel>()
    private val introViewModel by viewModels<IntroViewModel>()
    private val permissionViewModel by viewModels<PermissionViewModel>()
    private lateinit var retrofit: Retrofit
    private lateinit var supplementService: RetrofitService
    var waitTime = 0L

    private var permissionList =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            listOf<String>(
                Manifest.permission.CAMERA,
                Manifest.permission.NFC,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_MEDIA_IMAGES,
            )
        else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P)
            listOf<String>(
                Manifest.permission.CAMERA,
                Manifest.permission.NFC,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
        else listOf<String>(
            Manifest.permission.CAMERA,
            Manifest.permission.NFC,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, // 28이하에서만 요청해야함
        )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRetrofit()

        // 로그인 시도
        loginViewModel.tryLogin(this, supplementService)

        setContent {
            val dataString: String? = intent.dataString
            var sharedImageUrl = ""
            if (dataString != null) {
                Log.i("dataString", sharedImageUrl)
                sharedImageUrl = dataString.substring("naya://com.youme.naya/imageUrl=".length)
                Log.i("sharedImageUrl", sharedImageUrl)
            }
            if (introViewModel.isFirst.value != true) {
                if (!checkPermission()) {
                    Toast.makeText(this, "사용자에 의해 일부 권한이 수동으로 거부되었습니다.", Toast.LENGTH_SHORT)
                        .show()
                    Toast.makeText(this, "정상적인 이용이 제한될 수 있습니다.", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "설정에서 직접 권한을 해제해야합니다.", Toast.LENGTH_SHORT).show()
                }
            }
            AndroidTheme {
                RootNavigationGraph(
                    sharedImageUrl,
                    { checkPermission() },
                    rememberNavController(),
                    loginViewModel,
                    introViewModel,
                    permissionViewModel
                )
            }
        }
        introViewModel.loadIsFirst()

        permissionViewModel.loadTerms(this)
        permissionViewModel.loadPrivacy(this)

    }

    private fun checkPermission(): Boolean {
        var res = true
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        for (permission in permissionList) {
            if (checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                res = checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_DENIED;
                requestPermissions(permissionList.toTypedArray(), 0)
            }
            Log.i("Permission Check", "$permission processing -> $res")
        }
        return res;
    }

    // 이게 맞나
    override fun onBackPressed() {
        if (System.currentTimeMillis() - waitTime >= 1500) {
            waitTime = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
//            finish()
            finishAffinity()
        }
    }

    private fun initRetrofit() {
        retrofit = RetrofitClient.getInstance()
        supplementService = retrofit.create(RetrofitService::class.java)
    }
}
