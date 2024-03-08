package com.example.demo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.roomSetup.AddressRepo
import com.example.demo.roomSetup.LocalAddress
import com.example.demo.roomSetup.Product
import com.example.demo.roomSetup.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddressLocalVM(private val addressRepo: AddressRepo): ViewModel()  {

    private lateinit var address: LiveData<List<LocalAddress>>

    init {
        viewModelScope.launch {
            address = addressRepo.getAllAddress()
        }
    }

    fun insert(localAddress: LocalAddress) = viewModelScope.launch(Dispatchers.IO) {
        addressRepo.insertAddress(localAddress)
    }

     fun deleteAddress(localAddress: LocalAddress) = viewModelScope.launch {addressRepo.removeAddress(localAddress)}

       fun getList() = addressRepo.getAllAddress()


    fun remove(localAddress: LocalAddress){}

    fun insertWithOutDuplicate(list:List<LocalAddress>){
        viewModelScope.launch {

            val existingProduct = address.value

            if (!existingProduct.isNullOrEmpty()){
                val newListWithoutDuplicates = list.filter { newProduct ->
                    existingProduct!!.none { existingProduct -> existingProduct.id == newProduct.id }
                }

                if (newListWithoutDuplicates.isNotEmpty()) {
                    addressRepo.insertAddressWithConflictStrategy(newListWithoutDuplicates)
                }
            }

        }
    }

}
