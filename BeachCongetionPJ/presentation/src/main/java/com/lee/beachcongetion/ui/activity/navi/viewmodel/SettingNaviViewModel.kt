package com.lee.beachcongetion.ui.activity.navi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lee.beachcongetion.common.ResourceProvider
import com.lee.domain.usecase.GetCurrentNavi
import com.lee.domain.usecase.SetCurrentNavi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * 기본 네비게이션 설정 ViewModel class
 * **/
@HiltViewModel
class SettingNaviViewModel @Inject constructor(
    private val getCurrentNavi: GetCurrentNavi ,
    private val setCurrentNavi: SetCurrentNavi ,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _currentNavi = MutableLiveData<String>()
    val currentNavi : LiveData<String>
    get() = _currentNavi

    private val _selectedNavi = MutableLiveData<String>()
    val selectedNavi : LiveData<String>
    get() = _selectedNavi
    fun setSelectedNavi(navi: String){
        _selectedNavi.value = navi
    }

    /**
     * 선택된 기본 네비게이션을 가져오는 함수
     * **/
    fun getSelectedNavi() {
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                getCurrentNavi.invoke()
            }.collect{
                _currentNavi.value = it
            }
        }
    }

    /**
     * 기본 네비게이션을 선택하는 함수
     * **/
    fun setCurrentNavi() {
        viewModelScope.launch {
            setCurrentNavi.invoke(selectedNavi.value!!)
        }
    }
}