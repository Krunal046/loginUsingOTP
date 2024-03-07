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
import com.example.demo.model.addAddress.SendAddressModel
import com.example.demo.model.addressList.Address
import com.example.demo.mvvmSetup.ApiImplement
import com.example.demo.mvvmSetup.ViewModelFactory
import com.example.demo.retrofitSetup.ApiClient
import com.example.demo.retrofitSetup.ApiInterface
import com.example.demo.roomSetup.AddressRepo
import com.example.demo.roomSetup.AddressViewModelFactory
import com.example.demo.roomSetup.AppDatabase
import com.example.demo.utility.PreferencesManager
import com.example.demo.utility.Status
import com.example.demo.viewModel.AddAddressVM
import com.example.demo.viewModel.AddressLocalVM
import com.example.demo.viewModel.GetAddressVM
import com.example.demo.viewModel.LoginVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddressActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddressBinding
    private lateinit var vm:GetAddressVM
    private lateinit var addressAdapter: AddressAdapter
    private var addressList:ArrayList<Address> = ArrayList()
    private lateinit var localVM: AddressLocalVM
    private lateinit var addAddressVM: AddAddressVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addressDao = AppDatabase.getInstance(applicationContext).addressDao()
        val addressRepo = AddressRepo(addressDao)
        localVM = ViewModelProvider(
            this,
            AddressViewModelFactory(addressRepo)
        )[AddressLocalVM::class.java]

        addAddressVM = ViewModelProvider(
            this,
            ViewModelFactory(ApiImplement(ApiClient.client.create(ApiInterface::class.java)))
        )[AddAddressVM::class.java]

        val loaclList = localVM.getList()

        if (loaclList.isNotEmpty()){
            val token =  "Bearer "+PreferencesManager(this@AddressActivity).getAuthToken().toString()
            loaclList.forEach{
                if (!it.isUploaded){
                    val sendAddressModel: SendAddressModel = SendAddressModel(
                        address = it.address,
                        pincode = it.pincode,
                        Address_type = it.Address_type
                    )
                    addAddressVM.addAddress(sendAddressModel, token)
                    localVM.deleteAddress(it)
                }
            }
        }

        vm = ViewModelProvider(
            this,
            ViewModelFactory(ApiImplement(ApiClient.client.create(ApiInterface::class.java)))
        )[GetAddressVM::class.java]

        val token =  "Bearer "+PreferencesManager(this@AddressActivity).getAuthToken().toString()

        vm.getAddressList(token)

        addressAdapter = AddressAdapter(addressList)
        binding.rvAddress.layoutManager = LinearLayoutManager(this)
        binding.rvAddress.adapter = addressAdapter

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this@AddressActivity, AddAddressActivity::class.java)
            startActivity(intent)
        }

        binding.tvNextActivity.setOnClickListener{
            val intent = Intent(this@AddressActivity, ProductListingActivity::class.java)
            startActivity(intent)
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

        addAddressVM.obrAddAddress.observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    Log.e("Status", "SUCCESS")
                    if (vm != null){
                        val token =  "Bearer "+PreferencesManager(this@AddressActivity).getAuthToken().toString()
                        vm.getAddressList(token)
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

    override fun onResume() {
        super.onResume()
        if (vm != null){
            val token =  "Bearer "+PreferencesManager(this@AddressActivity).getAuthToken().toString()
            vm.getAddressList(token)
        }
    }
}
