package com.youme.naya.network

import com.youme.naya.vo.LoginInfoVO
import com.youme.naya.vo.LoginRequestVO
import com.youme.naya.vo.SendCardRequestVO
import com.youme.naya.vo.SendCardResponseVO
import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {
    @POST("user")
    fun updateLoginInfo(
        @Body payload: LoginRequestVO
    ): Call<LoginInfoVO>

    @POST("sendCard")
    fun sendCard(
        @Body payload: SendCardRequestVO
    ): Call<SendCardResponseVO>

}