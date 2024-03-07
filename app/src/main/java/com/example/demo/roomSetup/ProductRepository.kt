package com.example.demo.roomSetup

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class ProductRepository (private val userDao: ProductsDao) {

    val allGameList: LiveData<List<Product>> = userDao.getAllUsers()

     fun getAllProducts(): LiveData<List<Product>> = userDao.getAllUsers()
     fun insertProduct(product: Product) = userDao.insertUser(product)

     suspend fun insertProductsWithConflictStrategy(product: List<Product>) {
         withContext(Dispatchers.IO){
             userDao.insertProductsWithConflictStrategy(product)
         }
    }

}
