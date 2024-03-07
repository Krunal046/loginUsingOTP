package com.example.demo.model.addAddress

data class SendAddressModel(
    val address:String,
    val city_id:Int = 1,
    val state_id:Int = 1,
    val prof_zone_id:Int = 1,
    val pincode:String,
    val Address_type:String
)
