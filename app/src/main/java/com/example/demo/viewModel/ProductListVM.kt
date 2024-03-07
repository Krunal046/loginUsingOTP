package com.example.demo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo.model.productList.ProductListModel
import com.example.demo.mvvmSetup.ApiServiceRepo
import com.example.demo.utility.Resource
import com.example.demo.utility.Status
import com.example.demo.utility.onApiResponse

class ProductListVM(val repository: ApiServiceRepo?): ViewModel()  {

    var orbProductList = MutableLiveData<Resource<ProductListModel>>()

    fun getProductList(){
        repository?.getProductList(object : onApiResponse<ProductListModel>{
            override fun onSuccess(body: ProductListModel?) {
                orbProductList.postValue(Resource<ProductListModel>(Status.SUCCESS, body, null))
            }

            override fun onError(error: Throwable?) {
                orbProductList.postValue(Resource<ProductListModel>(Status.ERROR, null, error?.message.toString()))
            }

            override fun onLoading(msg: String) {
                orbProductList.postValue(Resource<ProductListModel>(Status.LOADING, null, msg))
            }

            override fun onFailed(msg: String) {
                orbProductList.postValue(Resource<ProductListModel>(Status.FAILED, null, msg))
            }
        })
    }

}
