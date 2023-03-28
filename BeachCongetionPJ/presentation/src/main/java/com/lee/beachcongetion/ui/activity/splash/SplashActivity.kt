package com.lee.beachcongetion.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lee.beachcongetion.R
import com.lee.beachcongetion.common.Utils
import com.lee.beachcongetion.databinding.ActivitySplashBinding
import com.lee.beachcongetion.ui.activity.main.MainActivity
import com.lee.beachcongetion.ui.activity.splash.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    private val viewModel : SplashViewModel by viewModels()
    private var permissionListener : PermissionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        checkNetwork()
    }

    override fun onDestroy() {
        permissionListener = null
        super.onDestroy()
    }

    /**
     * 네트워크 연결상태 확인 하는 함수
     * **/
    private fun checkNetwork() {
        if(Utils.checkNetworkConnection(this@SplashActivity)){
            if(permissionListener == null){
                permissionListener = PermissionListener()
            }
            Utils.checkPermission(permissionListener!!)
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
            lifecycleScope.launch(Dispatchers.IO){ // 권한 Preference 세팅하기
                viewModel.setPermission(false)
            }
            AlertDialog.Builder(this@SplashActivity)
                .setMessage("위치정보에 대한 권한이 필요합니다.")
                .setPositiveButton(getString(R.string.confirm)){ dialog , _ ->
                    Utils.checkPermission(this)
                    dialog.dismiss()
                }
                .create().show()
        }
    }

}