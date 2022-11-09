package com.youme.naya.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.youme.naya.network.RetrofitService
import com.youme.naya.vo.LoginInfoVO
import com.youme.naya.vo.LoginRequestVO
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableSharedFlow<Boolean>()
    var loginResult = _loginResult.asSharedFlow()

    fun tryLogin(context: Context, service: RetrofitService) {
        viewModelScope.launch {
            val account = async {
                getLastSignedInAccount(context)
            }
            Log.i("Login Launch", account.toString())
            delay(2500)
            setUserInfo(service)
            setLoginResult(account.await() != null)
        }
    }

    private fun setUserInfo(service: RetrofitService) {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            service.updateLoginInfo(
                LoginRequestVO(
                    auth.uid.toString(),
                    auth.currentUser!!.email.toString()
                )
            )
                .enqueue(object : Callback<LoginInfoVO> {
                    override fun onFailure(call: Call<LoginInfoVO>, t: Throwable) {
                        Log.d("TAG", "실패 : {$t}")
                    }

                    override fun onResponse(
                        call: Call<LoginInfoVO>,
                        response: Response<LoginInfoVO>
                    ) {
                        Log.i("JOIN CALL", call.toString())
                        Log.i("JOIN RESPONSE", response.toString())
                    }
                })
        }
    }

    private fun getLastSignedInAccount(context: Context) =
        GoogleSignIn.getLastSignedInAccount(context)


    private fun setLoginResult(isLogin: Boolean) {
        viewModelScope.launch {
            _loginResult.emit(isLogin)
        }
    }
}