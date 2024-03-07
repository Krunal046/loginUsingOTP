package com.example.demo.roomSetup

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class LocalAddress(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val address: String,
    val city_id:Int = 1,
    val state_id:Int = 1,
    val prof_zone_id:Int = 1,
    val pincode:String,
    val Address_type:String,
    val isUploaded:Boolean
)
