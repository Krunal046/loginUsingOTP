package com.example.demo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo.model.otpVerify.OtpCheckModel
import com.example.demo.model.otpVerify.OtpSendModel
import com.example.demo.mvvmSetup.ApiServiceRepo
import com.example.demo.utility.Resource
import com.example.demo.utility.Status
import com.example.demo.utility.onApiResponse

class OtpCheckVM(val repository: ApiServiceRepo?): ViewModel() {

    val obrOtpCheck = MutableLiveData<Resource<OtpCheckModel>>()

    fun otpCheck(otpSendModel: OtpSendModel){
        repository?.otpCheck(object : onApiResponse<OtpCheckModel> {
            override fun onSuccess(body: OtpCheckModel?) {
                obrOtpCheck.postValue(Resource<OtpCheckModel>(Status.SUCCESS, body, null))
            }

            override fun onError(error: Throwable?) {
                obrOtpCheck.postValue(Resource<OtpCheckModel>(Status.ERROR, null, error?.message.toString()))
            }

            override fun onLoading(msg: String) {
                obrOtpCheck.postValue(Resource<OtpCheckModel>(Status.LOADING, null, "Loading..."))
            }

            override fun onFailed(msg: String) {
                obrOtpCheck.postValue(Resource<OtpCheckModel>(Status.FAILED, null, msg.toString()))
            }

        },otpSendModel)
    }
}
