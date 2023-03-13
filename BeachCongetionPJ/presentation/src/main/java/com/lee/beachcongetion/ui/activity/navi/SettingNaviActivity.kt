package com.lee.beachcongetion.ui.activity.navi

import android.os.Bundle
import androidx.activity.viewModels
import com.lee.beachcongetion.R
import com.lee.beachcongetion.common.base.BaseActivity
import com.lee.beachcongetion.databinding.ActivitySettingNaviBinding
import com.lee.beachcongetion.ui.activity.navi.viewmodel.SettingNaviViewModel
import com.lee.data.common.Navi
import dagger.hilt.android.AndroidEntryPoint

/**
 * 기본 네비게이션 설정 activity
 * **/
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
            currentNavi.observe(this@SettingNaviActivity){ // 현재 설정된 기본 네비게이션
                val navi = when(it){
                    Navi.KAKAO_MAP.name -> {
                        getString(R.string.kakao_map)
                    }
                    Navi.TMAP.name -> {
                        getString(R.string.t_map)
                    }
                    else -> { // 기본값은 카카오맵
                        getString(R.string.kakao_map)
                    }
                }
                binding.selectedNaviTextView.text = String.format(getString(R.string.selected_navi) , navi)
            }
            selectedNavi.observe(this@SettingNaviActivity){ // 선택한 기본 네비게이션
                setCurrentNavi()
            }
        }
    }

    /**
     * Listener 등록 함수
     * **/
    override fun addListeners() {
        with(binding){
            kakaoMapLayout.setOnClickListener { // 카카오맵
                viewModel.setSelectedNavi(Navi.KAKAO_MAP.name)
                finish()
            }
            tMapLayout.setOnClickListener { // 티맵
                viewModel.setSelectedNavi(Navi.TMAP.name)
                finish()
            }
        }
    }
}