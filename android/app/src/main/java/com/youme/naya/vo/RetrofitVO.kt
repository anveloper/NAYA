package com.youme.naya.vo

import androidx.annotation.Keep

@Keep
data class LoginRequestVO(val userId: String, val email: String)
@Keep
data class LoginInfoVO(val userId: String, val email: String, val joinDate: String)
@Keep
data class SendCardRequestVO(val userId: String, val cardUrl: String)
@Keep
data class SendCardResponseVO(
    val sendCardId: Int,
    val userId: String,
    val cardUrl: String,
    val sendDatetime: String,
    val expiredDatetime: String
)
@Keep
data class MapRequestVO(val address: String?)
@Keep
data class MapResponseVO(
    val x:String?,
    val y:String?,
    val roadAddress:String?,
    val jibunAddress:String?
)