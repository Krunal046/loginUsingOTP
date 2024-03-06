package com.example.demo.model.addressList

data class Address(
    val address: String,
    val address_id: Int,
    val city: String,
    val google_address: Any,
    val locality: Any,
    val pincode: String,
    val state: String,
    val zone: String
)
