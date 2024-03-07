package com.example.demo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo.model.productDetails.ProductDeatilModel
import com.example.demo.mvvmSetup.ApiServiceRepo
import com.example.demo.utility.Resource
import com.example.demo.utility.Status
import com.example.demo.utility.onApiResponse

class ProductDetailVm(val repository: ApiServiceRepo?): ViewModel()  {

    var obrProductDetail = MutableLiveData<Resource<ProductDeatilModel>>()

    fun getProductDetail(id:String){
        repository?.getProductDetail(object : onApiResponse<ProductDeatilModel>{
            override fun onSuccess(body: ProductDeatilModel?) {
                obrProductDetail.postValue(Resource(Status.SUCCESS, body, null))
            }

            override fun onError(error: Throwable?) {
                obrProductDetail.postValue(Resource(Status.ERROR, null, error?.message.toString()))
            }

            override fun onLoading(msg: String) {
                obrProductDetail.postValue(Resource(Status.LOADING, null, msg))
            }

            override fun onFailed(msg: String) {
                obrProductDetail.postValue(Resource(Status.FAILED, null, msg))
            }
        },id)
    }

}
