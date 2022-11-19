package com.youme.naya.network

import com.youme.naya.vo.*
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

    @GET("map")
    fun map(
        @Query("address") address: String
    ): Call<MapResponseVO>
}