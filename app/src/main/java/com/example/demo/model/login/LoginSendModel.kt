package com.example.demo.model.login

data class LoginSendModel(
    val phone_no:Long,
    val clg_ref_id:String = " ",
    val token:String = "token",
    val grp_id:Int = 3
)
