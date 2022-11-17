package com.youme.naya

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.youme.naya.graphs.RootNavigationGraph
import com.youme.naya.login.LoginViewModel
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
    private lateinit var retrofit: Retrofit
    private lateinit var supplementService: RetrofitService
    var waitTime=0L

    override fun onBackPressed() {
        if(System.currentTimeMillis()-waitTime >= 1500){
            waitTime=System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show()
        }else{
//            finish()
            finishAffinity()
        }

    }


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
            AndroidTheme {
                RootNavigationGraph(sharedImageUrl, rememberNavController(), loginViewModel)
            }
        }
    }

    private fun initRetrofit() {
        retrofit = RetrofitClient.getInstance()
        supplementService = retrofit.create(RetrofitService::class.java)
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
//    RootNavigationGraph(rememberNavController(), null)
}