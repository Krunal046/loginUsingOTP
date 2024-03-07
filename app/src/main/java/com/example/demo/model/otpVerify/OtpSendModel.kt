package com.example.demo.model.otpVerify

data class OtpSendModel(
    val phone_no:Long,
    val otp:Int,
    val token:String = "token",
    val grp_id:Int = 3
)
