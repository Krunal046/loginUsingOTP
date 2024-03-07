package com.example.demo.mvvmSetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demo.model.productDetails.ProductDeatilModel
import com.example.demo.viewModel.AddAddressVM
import com.example.demo.viewModel.GetAddressVM
import com.example.demo.viewModel.LoginVM
import com.example.demo.viewModel.OtpCheckVM
import com.example.demo.viewModel.ProductDetailVm
import com.example.demo.viewModel.ProductListVM

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

            modelClass.isAssignableFrom(ProductDetailVm::class.java)->{
                return ProductDetailVm(apiServiceRepo) as T
            }

            modelClass.isAssignableFrom(ProductListVM::class.java)->{
                return ProductListVM(apiServiceRepo) as T
            }

            else ->{
                return modelClass as T
            }

        }
    }
}
