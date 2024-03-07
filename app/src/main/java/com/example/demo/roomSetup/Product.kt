package com.example.demo.roomSetup

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val uId: Long = 0,
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val title: String,
    val count: Int,
    val rate: Double
)
