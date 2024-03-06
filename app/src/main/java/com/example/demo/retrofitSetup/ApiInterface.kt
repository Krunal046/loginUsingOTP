package com.example.demo.retrofitSetup

import com.example.demo.model.addAddress.AddAddressModel
import com.example.demo.model.addAddress.AddressList
import com.example.demo.model.addressList.AddressListModel
import com.example.demo.model.login.LoginModel
import com.example.demo.model.login.LoginSendModel
import com.example.demo.model.otpVerify.OtpCheckModel
import com.example.demo.model.otpVerify.OtpSendModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @POST("pro_app/pro_login/")
    fun login(@Body loginSendModel: LoginSendModel): Deferred<Response<LoginModel>>

    @POST("pro_app/pro_otp_chk/")
    fun otpCheck(@Body otpSendModel: OtpSendModel):Deferred<Response<OtpCheckModel>>

    @POST("app/add_multiple_address_api")
    fun addressAdd(@Body hashMap: HashMap<String, String>):Deferred<Response<AddAddressModel>>

    @GET("app/add_multiple_address_api")
    fun getAddressList():Deferred<Response<AddressListModel>>

}
