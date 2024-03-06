package com.example.demo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo.model.login.LoginModel
import com.example.demo.model.login.LoginSendModel
import com.example.demo.mvvmSetup.ApiServiceRepo
import com.example.demo.utility.Resource
import com.example.demo.utility.Status
import com.example.demo.utility.onApiResponse

class LoginVM(val repository: ApiServiceRepo?): ViewModel() {

    var obrLogin = MutableLiveData<Resource<LoginModel>>()

    fun login(loginSendModel: LoginSendModel){
        repository?.login(object : onApiResponse<LoginModel> {
            override fun onSuccess(body: LoginModel?) {
                obrLogin.postValue(Resource<LoginModel>(Status.SUCCESS, body, null))
            }

            override fun onError(error: Throwable?) {
                obrLogin.postValue(Resource<LoginModel>(Status.ERROR, null, error?.message.toString()))
            }

            override fun onLoading(msg: String) {
                obrLogin.postValue(Resource<LoginModel>(Status.LOADING, null, "Loading.."))
            }

            override fun onFailed(msg: String) {
                obrLogin.postValue(Resource<LoginModel>(Status.FAILED, null, msg.toString()))
            }
        },loginSendModel)
    }

}
