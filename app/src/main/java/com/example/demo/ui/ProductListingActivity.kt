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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import com.example.demo.adapter.ProductAdapter
import com.example.demo.databinding.ActivityProductListingBinding
import com.example.demo.model.addressList.Address
import com.example.demo.model.productList.ProductListModelItem
import com.example.demo.model.productList.Rating
import com.example.demo.mvvmSetup.ApiImplement
import com.example.demo.mvvmSetup.ViewModelFactory
import com.example.demo.retrofitSetup.ApiClient
import com.example.demo.retrofitSetup.ApiInterface
import com.example.demo.roomSetup.AppDatabase
import com.example.demo.roomSetup.Product
import com.example.demo.roomSetup.ProductRepository
import com.example.demo.roomSetup.ProductViewModelFactory
import com.example.demo.utility.Status
import com.example.demo.viewModel.GetAddressVM
import com.example.demo.viewModel.ProductListVM
import com.example.demo.viewModel.ProductLoaclVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ProductListingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListingBinding
    private lateinit var vm: ProductListVM
    private lateinit var productAdapter: ProductAdapter
    private var productList: ArrayList<ProductListModelItem> = ArrayList()
    private lateinit var localVM: ProductLoaclVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productDao = AppDatabase.getInstance(applicationContext).productDao()
        val productRepository = ProductRepository(productDao)
        localVM = ViewModelProvider(
            this,
            ProductViewModelFactory(productRepository)
        )[ProductLoaclVM::class.java]

        vm = ViewModelProvider(
            this,
            ViewModelFactory(ApiImplement(ApiClient.clientForProduct.create(ApiInterface::class.java)))
        )[ProductListVM::class.java]

        if (isInternetConnected(this@ProductListingActivity)) {
            vm.getProductList()
        } else {
            showData()
        }

        productAdapter = ProductAdapter(this@ProductListingActivity, productList)

        binding.rvProduct.layoutManager = LinearLayoutManager(this)
        binding.rvProduct.adapter = productAdapter

        vm.orbProductList.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Status", "SUCCESS")
                    productList = it?.data as ArrayList<ProductListModelItem>
                    if (productAdapter != null) {
                        productAdapter.updateList(it.data)
                    }

                    val product = productList.map { productModel ->
                        Product(
                            id = productModel.id,
                            category = productModel.category,
                            description = productModel.description,
                            image = productModel.image,
                            price = productModel.price,
                            title = productModel.title,
                            count = productModel.rating.count,
                            rate = productModel.rating.rate
                        )
                    }

                    localVM.insertWithOutDuplicate(product)
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

    fun showData(){
        CoroutineScope(Dispatchers.Main).launch {
            localVM.getList().observe(this@ProductListingActivity){

                val productLocalList = it
                productLocalList.forEach {
                    val rating:Rating = Rating(
                        it.count,
                        it.rate
                    )
                    val productListModelItem: ProductListModelItem = ProductListModelItem(
                        category = it.category,
                        description = it.description,
                        id = it.id,
                        image = it.image,
                        price = it.price,
                        rating = rating,
                        title = it.title
                    )
                    productList.add(productListModelItem)
                    if (productAdapter != null) {
                        productAdapter.updateList(productList)
                    }
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
