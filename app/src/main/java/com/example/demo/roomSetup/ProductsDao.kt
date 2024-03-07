package com.example.demo.roomSetup

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products")
    fun getAllUsers(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(product: Product)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProductsWithConflictStrategy(users: List<Product>)

}
