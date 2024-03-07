package com.example.demo.model.addressList

data class Address(
    val address: String,
    val address_id: Int,
    val city: String,
    val google_address: String,
    val locality: String,
    val pincode: String,
    val state: String,
    val zone: String
)
