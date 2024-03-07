package com.example.demo.roomSetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.demo.viewModel.ProductLoaclVM

class ProductViewModelFactory(private val productRepository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(ProductLoaclVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductLoaclVM(productRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
