package com.lee.beachcongetion.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lee.beachcongetion.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        CoroutineScope(Dispatchers.Main).launch{
            startMainActivity()
        }


    }
    private suspend fun startMainActivity(){
        CoroutineScope(Dispatchers.Main).launch {
            delay(1500L)
            with(Intent(this@SplashActivity , MainActivity::class.java)){
                startActivity(this)
            }
            finish()
        }
    }

}