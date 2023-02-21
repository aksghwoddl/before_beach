package com.lee.beachcongetion.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lee.beachcongetion.data.repository.BeachRepository
import com.lee.beachcongetion.ui.viewmodel.BeachViewModel

class BeachViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(BeachViewModel::class.java)){
            BeachViewModel(BeachRepository()) as T
        } else {
            throw java.lang.IllegalArgumentException("ViewModel을 찾을수 없습니다.")
        }
    }
}