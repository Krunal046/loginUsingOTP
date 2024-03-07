package com.example.demo.roomSetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.demo.viewModel.AddressLocalVM
import com.example.demo.viewModel.ProductLoaclVM

class AddressViewModelFactory (private val addressRepo: AddressRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(AddressLocalVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddressLocalVM(addressRepo) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
