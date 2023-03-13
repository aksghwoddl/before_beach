package com.lee.beachcongetion.ui.activity.version

import android.os.Bundle
import com.lee.beachcongetion.BuildConfig
import com.lee.beachcongetion.R
import com.lee.beachcongetion.common.base.BaseActivity
import com.lee.beachcongetion.databinding.ActivityCheckVersionBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 버전 확인 액티비티
 * **/
@AndroidEntryPoint
class CheckVersionActivity : BaseActivity<ActivityCheckVersionBinding>(R.layout.activity_check_version){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val version = BuildConfig.VERSION_NAME
        binding.apply {
            versionTextView.text = String.format(getString(R.string.current_version) , version)
            checkVersionActivity = this@CheckVersionActivity
        }
    }

    /**
     * LiveData 관찰 함수
     * **/
    override fun observeData() {
    }

    /**
     * Listener 등록 함수
     * **/
    override fun addListeners() {
    }
}