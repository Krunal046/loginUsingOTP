package com.example.demo.roomSetup

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AddressDao {

    @Query("SELECT * FROM address")
    fun getAllAddress(): LiveData<List<LocalAddress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddress(address: LocalAddress)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAddressWithConflictStrategy(address: List<LocalAddress>)

    @Delete
    fun deleteAddress(address: LocalAddress)

}
