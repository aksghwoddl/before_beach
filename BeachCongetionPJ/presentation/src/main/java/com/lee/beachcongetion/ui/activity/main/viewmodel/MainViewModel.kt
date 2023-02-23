package com.lee.beachcongetion.ui.activity.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lee.domain.model.beach.BeachList
import com.lee.domain.model.kakao.Documents
import com.lee.domain.model.kakao.WcongDocuments
import com.lee.domain.usecase.GetBeachCongestion
import com.lee.domain.usecase.GetKakaoPoi
import com.lee.domain.usecase.GetWcong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBeachCongestion: GetBeachCongestion ,
    private val getKakaoPoi: GetKakaoPoi ,
    private val getWcong: GetWcong
) : ViewModel() {
    private val _beachList = MutableLiveData<BeachList>()
    val beachList : LiveData<BeachList>
    get() = _beachList

    private val _isProgress = MutableLiveData<Boolean>()
    val isProgress : LiveData<Boolean>
    get() = _isProgress
    fun setIsProgress(on : Boolean) {
        _isProgress.value = on
    }

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage : LiveData<String>
    get() = _toastMessage
    fun setToastMessage(message : String){
        _toastMessage.value = message
    }

    private val _poiList = MutableLiveData<ArrayList<Documents>>()
    val poiList : LiveData<ArrayList<Documents>>
    get() = _poiList
    fun setPoiList(list : ArrayList<Documents>){
        _poiList.value = list
    }

    private val _wcongList = MutableLiveData<ArrayList<WcongDocuments>>()
    val wcongList : LiveData<ArrayList<WcongDocuments>>
    get() = _wcongList

    private val exceptionHandler = CoroutineExceptionHandler{
        _ , throwExceptionHandler -> throwExceptionHandler.localizedMessage?.let { _toastMessage.value = it }
    }

    fun getWcongPoint(key : String, x : String, y : String) {
        viewModelScope.launch {
            val wcong = getWcong.invoke(key , x , y)
            _wcongList.value = wcong.documents
        }
    }

}