package com.lee.beachcongetion.ui.fragment.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lee.beachcongetion.common.ResourceProvider
import com.lee.domain.model.beach.BeachList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _beachList = MutableLiveData<BeachList>()
    val beachList : LiveData<BeachList>
    get() = _beachList
    fun setBeachList(list : BeachList){
        _beachList.value = list
    }

}