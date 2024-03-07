package com.example.demo.mvvmSetup

import com.example.demo.model.addAddress.AddAddressModel
import com.example.demo.model.addAddress.SendAddressModel
import com.example.demo.model.addressList.AddressListModel
import com.example.demo.model.login.LoginModel
import com.example.demo.model.login.LoginSendModel
import com.example.demo.model.otpVerify.OtpCheckModel
import com.example.demo.model.otpVerify.OtpSendModel
import com.example.demo.utility.onApiResponse

interface ApiServiceRepo {

    fun login(callBack:onApiResponse<LoginModel>, loginSendModel: LoginSendModel)

    fun otpCheck(callBack:onApiResponse<OtpCheckModel>,    otpSendModel: OtpSendModel)

    fun addAddress(callBack:onApiResponse<AddAddressModel>,  sendAddressModel: SendAddressModel)

    fun getAddressList(callBack: onApiResponse<AddressListModel>)

}
