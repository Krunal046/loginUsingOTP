package com.example.demo.model.addAddress

data class AddressList(
    val Address_type: String,
    val address: String,
    val address_id: Int,
    val caller_id: Int,
    val city_id: Int,
    val google_address: Any,
    val last_modified_by: Any,
    val locality: Any,
    val patient_id: Any,
    val pincode: String,
    val prof_zone_id: Int,
    val state_id: Int,
    val status: Int
)
