package com.example.demo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo.model.addressList.AddressListModel
import com.example.demo.mvvmSetup.ApiServiceRepo
import com.example.demo.utility.Resource
import com.example.demo.utility.Status
import com.example.demo.utility.onApiResponse

class GetAddressVM(val repository: ApiServiceRepo?): ViewModel() {

    val obrAddressList = MutableLiveData<Resource<AddressListModel>>()

    fun getAddressList(){
        repository?.getAddressList(object : onApiResponse<AddressListModel> {
            override fun onSuccess(body: AddressListModel?) {
                obrAddressList.postValue(Resource<AddressListModel>(Status.SUCCESS, body, null))
            }

            override fun onError(error: Throwable?) {
                obrAddressList.postValue(Resource<AddressListModel>(Status.ERROR, null, error?.message.toString()))
            }

            override fun onLoading(msg: String) {
                obrAddressList.postValue(Resource<AddressListModel>(Status.LOADING, null, "Loading..."))
            }

            override fun onFailed(msg: String) {
                obrAddressList.postValue(Resource<AddressListModel>(Status.FAILED, null, msg.toString()))
            }

        })
    }
}
