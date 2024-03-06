package com.example.demo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.demo.R
import com.example.demo.databinding.ActivityLoginBinding
import com.example.demo.model.login.LoginSendModel
import com.example.demo.mvvmSetup.ApiImplement
import com.example.demo.mvvmSetup.ApiServiceRepo
import com.example.demo.mvvmSetup.ViewModelFactory
import com.example.demo.retrofitSetup.ApiClient
import com.example.demo.retrofitSetup.ApiInterface
import com.example.demo.utility.PreferencesManager
import com.example.demo.utility.Status
import com.example.demo.viewModel.LoginVM

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var vm: LoginVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(
            this,
            ViewModelFactory(ApiImplement(ApiClient.client.create(ApiInterface::class.java)))
        )[LoginVM::class.java]

        binding.btnLogin.setOnClickListener {
            val mobileNo = binding.etMobileNo.toString()
            mobileNo.trim()
            if (mobileNo.isNullOrBlank()){
                Toast.makeText(this@Login, "please enter mobile number. ", Toast.LENGTH_SHORT).show()
            }else{
                if (mobileNo.length == 10){
                    val loginSendModel:LoginSendModel = LoginSendModel(phone_no = mobileNo.toInt())
                    vm.login(loginSendModel)
                }else{
                    Toast.makeText(this@Login, "enter valid mobile number. ", Toast.LENGTH_SHORT).show()
                }
            }
        }

        vm.obrLogin.observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    Log.e("Status", "SUCCESS")
                    PreferencesManager(this@Login).setLoggedInStatus(true)
                    val otp = it.data?.Res_Data?.OTP
                    val mobileNo = it.data?.Res_Data?.phone_no
                    val intent = Intent(this@Login, OtpCheckActivity::class.java)
                    intent.putExtra("otp", otp)
                    intent.putExtra("mobileNo", mobileNo)
                    startActivity(intent)
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

}

