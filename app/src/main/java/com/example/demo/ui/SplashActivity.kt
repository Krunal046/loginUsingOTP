package com.example.demo.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.utility.PreferencesManager

class SplashActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        val isLogin = PreferencesManager(this).isLoggedIn()

        Handler().postDelayed({
            if (isLogin){
                val i = Intent(
                    this@SplashActivity,
                    AddressActivity::class.java
                )
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
                finish()
            }else{
                val i = Intent(
                    this@SplashActivity,
                    Login::class.java
                )
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
                finish()
            }
        }, 2000)


    }
}
