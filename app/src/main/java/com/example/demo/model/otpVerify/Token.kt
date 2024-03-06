package com.example.demo.model.otpVerify

data class Token(
    val access: String,
    val colleague: Colleague,
    val refresh: String
)
