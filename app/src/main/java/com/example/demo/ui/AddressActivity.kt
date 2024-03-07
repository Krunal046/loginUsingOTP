package com.example.demo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import com.example.demo.adapter.AddressAdapter
import com.example.demo.databinding.ActivityAddressBinding
import com.example.demo.model.addressList.Address
import com.example.demo.mvvmSetup.ApiImplement
import com.example.demo.mvvmSetup.ViewModelFactory
import com.example.demo.retrofitSetup.ApiClient
import com.example.demo.retrofitSetup.ApiInterface
import com.example.demo.utility.PreferencesManager
import com.example.demo.utility.Status
import com.example.demo.viewModel.GetAddressVM
import com.example.demo.viewModel.LoginVM

class AddressActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddressBinding
    private lateinit var vm:GetAddressVM
    private lateinit var addressAdapter: AddressAdapter
    private var addressList:ArrayList<Address> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(
            this,
            ViewModelFactory(ApiImplement(ApiClient.client.create(ApiInterface::class.java)))
        )[GetAddressVM::class.java]

        vm.getAddressList()

        addressAdapter = AddressAdapter(addressList)
        binding.rvAddress.layoutManager = LinearLayoutManager(this)
        binding.rvAddress.adapter = addressAdapter

        binding.floatingActionButton.setOnClickListener {

        }

        vm.obrAddressList.observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    Log.e("Status", "SUCCESS")
                    addressList = it?.data?.address_list as ArrayList<Address>
                    if (addressAdapter != null && binding.rvAddress.adapter != null){
                        addressAdapter.updateList(it?.data?.address_list)
                    }
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
}
