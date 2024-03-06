package com.example.demo.mvvmSetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demo.viewModel.AddAddressVM
import com.example.demo.viewModel.GetAddressVM
import com.example.demo.viewModel.LoginVM
import com.example.demo.viewModel.OtpCheckVM

class ViewModelFactory(val apiServiceRepo: ApiServiceRepo): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(LoginVM::class.java)->{
                return LoginVM(apiServiceRepo) as T
            }

            modelClass.isAssignableFrom(OtpCheckVM::class.java)->{
                return OtpCheckVM(apiServiceRepo) as T
            }

            modelClass.isAssignableFrom(AddAddressVM::class.java)->{
                return AddAddressVM(apiServiceRepo) as T
            }

            modelClass.isAssignableFrom(GetAddressVM::class.java)->{
                return GetAddressVM(apiServiceRepo) as T
            }

            else ->{
                return modelClass as T
            }

        }
    }
}
