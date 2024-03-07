package com.example.demo.retrofitSetup

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient  {

    val BASE_URL = "http://103.186.133.168:8008/"
    val BASE_URL_FOR_PRODUCT = "https://fakestoreapi.com/"

    lateinit var retrofit: Retrofit

    val client: Retrofit
        get() {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(getHttpClient())
                .build()
            return retrofit
        }

    val clientForProduct: Retrofit
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_FOR_PRODUCT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(getHttpClient())
                .build()
            return retrofit
        }

    fun getHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .writeTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .connectTimeout(10, TimeUnit.MINUTES)
            .build()
        return client
    }

}
