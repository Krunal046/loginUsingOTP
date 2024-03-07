package com.example.demo.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.databinding.ActivityProductDetailBinding
import com.example.demo.model.productList.ProductListModelItem
import com.example.demo.mvvmSetup.ApiImplement
import com.example.demo.mvvmSetup.ViewModelFactory
import com.example.demo.retrofitSetup.ApiClient
import com.example.demo.retrofitSetup.ApiInterface
import com.example.demo.utility.Status
import com.example.demo.viewModel.ProductDetailVm
import com.example.demo.viewModel.ProductListVM

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityProductDetailBinding
    private lateinit var vm:ProductDetailVm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getStringExtra("productId")

        val bundle = intent.extras

        vm = ViewModelProvider(
            this,
            ViewModelFactory(ApiImplement(ApiClient.clientForProduct.create(ApiInterface::class.java)))
        )[ProductDetailVm::class.java]

        if (isInternetConnected(this@ProductDetailActivity)){
            vm.getProductDetail(productId!!)
        }else{
            if (bundle != null) {
                val productId = bundle.getString("productId")
                val productTitle = bundle.getString("productTitle")
                val productPrice = bundle.getDouble("productPrice")
                val productDescription = bundle.getString("productDescription")
                val productRating = bundle.getFloat("productRating")
                val productImage = bundle.getString("productImage")

                Glide.with(this)
                    .load(productImage)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(binding.imageViewProduct)

                binding.textViewTitle.text = productTitle
                binding.textViewPrice.text = productPrice.toString()
                binding.textViewDescription.text = productDescription
                binding.ratingBar.rating = productRating
            }
        }

        vm.obrProductDetail.observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    Log.e("Status", "SUCCESS")
                    val productDetailModel = it?.data

                    if (productDetailModel != null){
                        Glide.with(this)
                            .load(productDetailModel?.image)
                            .placeholder(R.mipmap.ic_launcher)
                            .into(binding.imageViewProduct)

                        binding.textViewTitle.text = productDetailModel?.title
                        binding.textViewPrice.text = productDetailModel?.price.toString()
                        binding.textViewDescription.text = productDetailModel?.description
                        binding.ratingBar.rating = productDetailModel?.rating?.rate?.toFloat()!!
                    }

                }
                Status.ERROR -> {
                    Log.e("Status", "ERROR")
                }
                Status.FAILED -> {
                    Log.e("Status", "FAILED")
                }
                Status.LOADING -> {
                    Log.e("Status", "LOADING")
                }
            }
        }

    }

    private fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}
