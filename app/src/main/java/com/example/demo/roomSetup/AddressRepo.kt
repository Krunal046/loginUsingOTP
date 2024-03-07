package com.example.demo.roomSetup

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddressRepo(private val addressRepo:AddressDao) {

    fun getAllAddress(): LiveData<List<LocalAddress>> = addressRepo.getAllAddress()
    fun insertAddress(address: LocalAddress) = addressRepo.insertAddress(address)

    fun removeAddress(localAddress: LocalAddress) = addressRepo.deleteAddress(localAddress)

    suspend fun insertAddressWithConflictStrategy(list: List<LocalAddress>) {
        withContext(Dispatchers.IO){
            addressRepo.insertAddressWithConflictStrategy(list)
        }
    }

}
