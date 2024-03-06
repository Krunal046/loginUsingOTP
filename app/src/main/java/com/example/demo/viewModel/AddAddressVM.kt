package com.example.demo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo.model.addAddress.AddAddressModel
import com.example.demo.mvvmSetup.ApiServiceRepo
import com.example.demo.utility.Resource
import com.example.demo.utility.Status
import com.example.demo.utility.onApiResponse

class AddAddressVM(val repository: ApiServiceRepo?): ViewModel() {

    val obrAddAddress = MutableLiveData<Resource<AddAddressModel>>()

    fun addAddress(hashMap: HashMap<String,String>){
        repository?.addAddress(object :onApiResponse<AddAddressModel>{
            override fun onSuccess(body: AddAddressModel?) {
                obrAddAddress.postValue(Resource<AddAddressModel>(Status.SUCCESS, body, null))
            }

            override fun onError(error: Throwable?) {
                obrAddAddress.postValue(Resource<AddAddressModel>(Status.ERROR, null,  error?.message.toString()))
            }

            override fun onLoading(msg: String) {
                obrAddAddress.postValue(Resource<AddAddressModel>(Status.LOADING, null, "Loading..."))
            }

            override fun onFailed(msg: String) {
                obrAddAddress.postValue(Resource<AddAddressModel>(Status.FAILED, null,  msg.toString()))
            }
        }, hashMap)
    }
}
