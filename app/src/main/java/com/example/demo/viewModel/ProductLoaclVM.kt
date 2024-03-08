package com.example.demo.viewModel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.roomSetup.Product
import com.example.demo.roomSetup.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.Policy

class ProductLoaclVM(private val productRepository: ProductRepository):ViewModel() {

    private lateinit var products:LiveData<List<Product>>

    init {
        viewModelScope.launch {
            products = productRepository.getAllProducts()
        }
    }

    fun insert(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        productRepository.insertProduct(product)
    }

    fun getList() = productRepository.getAllProducts()

    fun insertWithOutDuplicate(list:List<Product>, existingProduct:List<Product> ){
        viewModelScope.launch {

            if (!existingProduct.isNullOrEmpty()){
                val newListWithoutDuplicates = list.filter { newProduct ->
                    existingProduct!!.none { existingProduct -> existingProduct.id == newProduct.id }
                }

                if (newListWithoutDuplicates.isNotEmpty()) {
                    productRepository.insertProductsWithConflictStrategy(newListWithoutDuplicates)
                }
            }else{
                productRepository.insertProductsWithConflictStrategy(list)
            }

        }
    }

/*    fun insertOrUpdateUsers(users: List<Product>) {
        viewModelScope.launch {
            val existingProduct = productRepository.getAllProducts()

            val newProduct = users.filterNot { newProduct ->
                existingProduct.any { existingProduct -> existingProduct.id == newProduct.id }
            }

            if (newProduct.isNotEmpty()) {
                productRepository.insertProductsWithConflictStrategy(newProduct)
            }

            _products.value = productRepository.getAllProducts()
        }
    }*/

  /*  fun insertUser(product: Product) {
        viewModelScope.launch {
            productRepository.insertProduct(product)
            _products.value = productRepository.getAllProducts()
        }
    }*/

}
