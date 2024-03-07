package com.example.demo.mvvmSetup

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.demo.model.addAddress.AddAddressModel
import com.example.demo.model.addAddress.SendAddressModel
import com.example.demo.model.addressList.AddressListModel
import com.example.demo.model.login.LoginModel
import com.example.demo.model.login.LoginSendModel
import com.example.demo.model.otpVerify.OtpCheckModel
import com.example.demo.model.otpVerify.OtpSendModel
import com.example.demo.model.productDetails.ProductDeatilModel
import com.example.demo.model.productList.ProductListModel
import com.example.demo.retrofitSetup.ApiInterface
import com.example.demo.utility.onApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ApiImplement(val apiInterface: ApiInterface) : ApiServiceRepo {

    override fun login(callBack: onApiResponse<LoginModel>, loginSendModel: LoginSendModel) {
        commonResponse(callBack, apiInterface.login(loginSendModel))
    }

    override fun otpCheck(
        callBack: onApiResponse<OtpCheckModel>,
        otpSendModel: OtpSendModel
    ) {
        commonResponse(callBack, apiInterface.otpCheck(otpSendModel))
    }

    override fun addAddress(
        callBack: onApiResponse<AddAddressModel>,
        sendAddressModel: SendAddressModel,
        bearerToken: String
    ) {
        commonResponse(callBack, apiInterface.addressAdd(sendAddressModel, bearerToken))
    }

    override fun getAddressList(callBack: onApiResponse<AddressListModel>, bearerToken: String) {
        commonResponse(callBack, apiInterface.getAddressList(bearerToken))
    }

    override fun getProductList(callBack: onApiResponse<ProductListModel>) {
        commonResponse(callBack, apiInterface.getProductList())
    }

    override fun getProductDetail(callBack: onApiResponse<ProductDeatilModel>, id: String) {
        commonResponse(callBack, apiInterface.getProductDetail(id))
    }


    private fun <T> commonResponse(
        callback: onApiResponse<T>,
        response: Deferred<Response<T>>
    ) {
        callback.onLoading("Loading...")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                withContext(Dispatchers.IO) {
                    val fullResponse = response.await()
                    if (fullResponse.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            callback.onSuccess(fullResponse.body())
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            failure(callback, fullResponse)
                        }
                    }
                }
            } catch (error: Throwable) {
                withContext(Dispatchers.Main) {
                    callback.onError(error)
                }
            }
        }
    }

    fun <T> failure(
        callback: onApiResponse<T>,
        response: Response<T>
    ) {
        callback.onFailed("Failed")
    }
}
