package com.lee.beachcongetion.ui.activity.splash.viewmodel

import androidx.lifecycle.ViewModel
import com.lee.domain.usecase.SetIsPermission
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Splash 화면 ViewModel
 * **/
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val setIsPermission: SetIsPermission
) : ViewModel() {

    /**
     * 권한에 대한 Preference를 설정한다.
     * **/
    suspend fun setPermission(permission : Boolean){
        setIsPermission.invoke(permission)
    }
}