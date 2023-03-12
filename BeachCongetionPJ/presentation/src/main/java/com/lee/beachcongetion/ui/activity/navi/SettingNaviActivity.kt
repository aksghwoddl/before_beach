package com.lee.beachcongetion.ui.activity.navi

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.lee.beachcongetion.R
import com.lee.beachcongetion.common.Navi
import com.lee.beachcongetion.common.base.BaseActivity
import com.lee.beachcongetion.databinding.ActivitySettingNaviBinding
import com.lee.beachcongetion.ui.activity.navi.viewmodel.SettingNaviViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 기본 네비게이션 설정 activity
 * **/
private const val TAG = "SettingNaviActivity"
@AndroidEntryPoint
class SettingNaviActivity : BaseActivity<ActivitySettingNaviBinding>(R.layout.activity_setting_navi) {
    private val viewModel : SettingNaviViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            settingNaviActivity = this@SettingNaviActivity
        }
        viewModel.getSelectedNavi()
    }

    /**
     * LiveData 관찰 함수
     * **/
    override fun observeData() {
        with(viewModel){
            currentNavi.observe(this@SettingNaviActivity){
                Log.d(TAG, "observeData: $it")
            }
            selectedNavi.observe(this@SettingNaviActivity){
                setCurrentNavi()
            }
        }
    }

    /**
     * Listener 등록 함수
     * **/
    override fun addListeners() {
        with(binding){
            kakaoMapLayout.setOnClickListener {
                viewModel.setSelectedNavi(Navi.KAKAO_MAP.name)
            }
        }
    }
}