package com.example.demo.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.demo.R
import com.example.demo.databinding.ActivityOtpCheckBinding
import com.example.demo.model.otpVerify.OtpSendModel
import com.example.demo.mvvmSetup.ApiImplement
import com.example.demo.mvvmSetup.ViewModelFactory
import com.example.demo.retrofitSetup.ApiClient
import com.example.demo.retrofitSetup.ApiInterface
import com.example.demo.utility.PreferencesManager
import com.example.demo.utility.Status
import com.example.demo.viewModel.LoginVM
import com.example.demo.viewModel.OtpCheckVM

class OtpCheckActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpCheckBinding
    private lateinit var vm: OtpCheckVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(
            this,
            ViewModelFactory(ApiImplement(ApiClient.client.create(ApiInterface::class.java)))
        )[OtpCheckVM::class.java]

        val otp = intent.getStringExtra("otp")
        val mobileNo = intent.getStringExtra("mobileNo")

        binding.tvOtpCheckMobileNo.text = "Sent on +91 $mobileNo"

        binding.otpBox1.addTextChangedListener(OTPTextWatcher(binding.otpBox2))
        binding.otpBox2.addTextChangedListener(OTPTextWatcher(binding.otpBox3))
        binding.otpBox3.addTextChangedListener(OTPTextWatcher(binding.otpBox4))

        binding.otpBox1.requestFocus()

        binding.btnOtpCheck.setOnClickListener {
            val enteredOtp =
                binding.otpBox1.text.toString() + binding.otpBox2.text.toString() + binding.otpBox3.text.toString() + binding.otpBox4.text.toString()
            enteredOtp.trim()
            if (enteredOtp.isNullOrBlank() && enteredOtp.length < 4) {
                Toast.makeText(this@OtpCheckActivity, "please enter otp ", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val otpSendModel:OtpSendModel = OtpSendModel(phone_no = mobileNo!!.toLong(), otp = enteredOtp.toInt())
                vm.otpCheck(otpSendModel)
            }
        }

        vm.obrOtpCheck.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Status", "SUCCESS")
                    PreferencesManager(this@OtpCheckActivity).saveAuthToken(it.data?.token?.access!!)
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

    inner class OTPTextWatcher(private val nextEditText: EditText) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s?.length == 1) {
                nextEditText.requestFocus()
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

}
