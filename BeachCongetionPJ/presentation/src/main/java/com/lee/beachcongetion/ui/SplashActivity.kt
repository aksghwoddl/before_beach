package com.lee.beachcongetion.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gun0912.tedpermission.normal.TedPermission
import com.lee.beachcongetion.R
import com.lee.beachcongetion.common.Utils
import com.lee.beachcongetion.databinding.ActivitySplashBinding
import com.lee.beachcongetion.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
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
        checkNetwork()
    }

    /**
     * 네트워크 연결상태 확인 하는 함수
     * **/
    private fun checkNetwork() {
        if(Utils.checkNetworkConnection(this@SplashActivity)){
            checkPermission()
        } else {
            AlertDialog.Builder(this@SplashActivity)
                .setTitle(getString(R.string.network))
                .setMessage(getString(R.string.dialog_check_network))
                .setPositiveButton(getString(R.string.confirm)){ dialog , _ ->
                    checkNetwork()
                    dialog.dismiss()
                }.create().show()
        }
    }

    /**
     * 앱 권한 확인하는 함수
     * **/
    private fun checkPermission() {
        val tedPermission = TedPermission.create()
        tedPermission.run {
            setPermissionListener(PermissionListener())
            setDeniedMessage("위치정보에 대한 권한이 필요합니다.")
            setPermissions(ACCESS_FINE_LOCATION , ACCESS_COARSE_LOCATION)
            check()
        }
    }

    /**
     * 권한을 확인하는 Listener
     * **/
    private inner class PermissionListener : com.gun0912.tedpermission.PermissionListener {
        override fun onPermissionGranted() {
            lifecycleScope.launch {
                delay(1000L)
                with(Intent(this@SplashActivity , MainActivity::class.java)){
                    startActivity(this)
                    finish()
                }
            }

        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            AlertDialog.Builder(this@SplashActivity)
                .setMessage("위치정보에 대한 권한이 필요합니다.")
                .setPositiveButton(getString(R.string.confirm)){ dialog , _ ->
                    checkPermission()
                    dialog.dismiss()
                }
                .create().show()
        }
    }

}