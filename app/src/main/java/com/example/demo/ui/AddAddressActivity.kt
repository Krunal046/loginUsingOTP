package com.example.demo.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.demo.R
import com.example.demo.databinding.ActivityAddAddressBinding
import com.example.demo.model.addAddress.SendAddressModel
import com.example.demo.mvvmSetup.ApiImplement
import com.example.demo.mvvmSetup.ViewModelFactory
import com.example.demo.retrofitSetup.ApiClient
import com.example.demo.retrofitSetup.ApiInterface
import com.example.demo.roomSetup.AddressRepo
import com.example.demo.roomSetup.AddressViewModelFactory
import com.example.demo.roomSetup.AppDatabase
import com.example.demo.roomSetup.LocalAddress
import com.example.demo.roomSetup.Product
import com.example.demo.roomSetup.ProductRepository
import com.example.demo.roomSetup.ProductViewModelFactory
import com.example.demo.utility.PreferencesManager
import com.example.demo.utility.Status
import com.example.demo.viewModel.AddAddressVM
import com.example.demo.viewModel.AddressLocalVM
import com.example.demo.viewModel.ProductLoaclVM

class AddAddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAddressBinding
    private lateinit var vm: AddAddressVM
    private lateinit var localVM:AddressLocalVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addressDao = AppDatabase.getInstance(applicationContext).addressDao()
        val addressRepo = AddressRepo(addressDao)
        localVM = ViewModelProvider(
            this,
            AddressViewModelFactory(addressRepo)
        )[AddressLocalVM::class.java]

        vm = ViewModelProvider(
            this,
            ViewModelFactory(ApiImplement(ApiClient.client.create(ApiInterface::class.java)))
        )[AddAddressVM::class.java]


        binding.btnSubmit.setOnClickListener {
            val address = binding.etAddress.text.toString()
            val pinCode = binding.etPinCode.text.toString()
            val addressType = binding.etAddressType.toString()

            address.trim()
            pinCode.trim()
            addressType.trim()

            if (address.isNullOrEmpty() || pinCode.isNullOrEmpty() || addressType.isNullOrEmpty()) {
                Toast.makeText(this@AddAddressActivity, "Please Enter Data. ", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val sendAddressModel: SendAddressModel = SendAddressModel(
                    address = address,
                    pincode = pinCode,
                    Address_type = addressType
                )

                val token =  "Bearer "+PreferencesManager(this@AddAddressActivity).getAuthToken().toString()

                if (isInternetConnected(this@AddAddressActivity)){
                    vm.addAddress(sendAddressModel,token)
                } else{
                    val localAddress:LocalAddress = LocalAddress(
                        address = address,
                        pincode = pinCode,
                        isUploaded = false,
                        Address_type = addressType

                    )
                    localVM.insert(localAddress)
                }

                finish()

            }
        }

        vm.obrAddAddress.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Status", "SUCCESS")
                }

                Status.ERROR -> {
                    Log.e("Status", "ERROR")
                }

                Status.FAILED -> {
                    Log.e("Status", "FAILED")
                }

                Status.LOADING -> {
                    Log.e("Status", "LOADING")
                }
            }
        }

    }

    private fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}
