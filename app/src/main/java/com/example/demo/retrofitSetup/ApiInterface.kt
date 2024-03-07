package com.example.demo.retrofitSetup

import com.example.demo.model.addAddress.AddAddressModel
import com.example.demo.model.addAddress.AddressList
import com.example.demo.model.addAddress.SendAddressModel
import com.example.demo.model.addressList.AddressListModel
import com.example.demo.model.login.LoginModel
import com.example.demo.model.login.LoginSendModel
import com.example.demo.model.otpVerify.OtpCheckModel
import com.example.demo.model.otpVerify.OtpSendModel
import com.example.demo.model.productDetails.ProductDeatilModel
import com.example.demo.model.productList.ProductListModel
import com.example.demo.model.productList.ProductListModelItem
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiInterface {

    @POST("pro_app/pro_login/")
    fun login(@Body loginSendModel: LoginSendModel): Deferred<Response<LoginModel>>

    @POST("pro_app/pro_otp_chk/")
    fun otpCheck(@Body otpSendModel: OtpSendModel):Deferred<Response<OtpCheckModel>>

    @POST("app/add_multiple_address_api")
    fun addressAdd(@Body sendAddressModel: SendAddressModel,  @Header("Authorization") bearerToken: String):Deferred<Response<AddAddressModel>>

    @GET("app/add_multiple_address_api")
    fun getAddressList(@Header("Authorization") bearerToken: String):Deferred<Response<AddressListModel>>

    @GET("products")
    fun getProductList():Deferred<Response<ProductListModel>>

    @GET("products/{id}")
    fun getProductDetail(@Path("id") id:String):Deferred<Response<ProductDeatilModel>>

}
