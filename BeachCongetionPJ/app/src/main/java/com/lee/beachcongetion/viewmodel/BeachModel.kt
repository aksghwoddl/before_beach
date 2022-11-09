package com.lee.beachcongetion.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lee.beachcongetion.retrofit.model.BeachCongestionList
import com.lee.beachcongetion.retrofit.model.BeachCongestionModel

class BeachModel : ViewModel() {
    private val _beachList = MutableLiveData<MutableList<BeachCongestionModel>>()
    val beachList : LiveData<MutableList<BeachCongestionModel>>
    get() = _beachList

    fun setBeachViewModelList(list : MutableList<BeachCongestionModel>) {
        _beachList.value = list
    }

}