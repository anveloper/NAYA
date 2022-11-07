package com.youme.naya.vo

data class LoginRequestVO(val userId: String, val email: String)
data class LoginInfoVO(val userId: String, val email: String, val joinDate: String)

data class SendCardRequestVO(val userId: String, val cardUrl: String)
data class SendCardResponseVO(
    val sendCardId: Int,
    val userId: String,
    val cardUrl: String,
    val sendDatetime: String,
    val expiredDatetime: String
)