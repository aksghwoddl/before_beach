package com.lee.beachcongetion.ui.activity.main.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lee.domain.model.beach.BeachList
import com.lee.domain.model.kakao.CurrentLatLng
import com.lee.domain.model.kakao.Documents
import com.lee.domain.model.kakao.Wcong
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

    private val _currentLocation = MutableLiveData<Location>()
    val currentLocation : LiveData<Location>
    get() = _currentLocation
    fun setCurrentLocation(location : Location){
        _currentLocation.value = location
    }

    private val _currentLatLng = MutableLiveData<CurrentLatLng>()
    val currentLatLng : LiveData<CurrentLatLng>
    get() = _currentLatLng
    fun setCurrentLatLng(latLng: CurrentLatLng) {
        _currentLatLng.value = latLng
    }

    private val exceptionHandler = CoroutineExceptionHandler{
        _ , throwExceptionHandler -> throwExceptionHandler.localizedMessage?.let { _toastMessage.value = it }
    }

    /**
     * 해수욕장 혼잡도 불러오기
     * **/
    fun getAllBeachCongestion() {
        _isProgress.value = true
        viewModelScope.launch(exceptionHandler) {
            val beachList = getBeachCongestion.invoke()
            _beachList.value = beachList
            _isProgress.value = false
        }
    }

    /**
     * 선택된 장소의 POI 정보 불러오기
     * **/
    fun getKakaoPoiList(key : String , keyword : String) {
        _isProgress.value = true
        viewModelScope.launch {
            val kakaoPoi = getKakaoPoi.invoke(key , keyword)
            _poiList.value = kakaoPoi.documents
            _isProgress.value = false
        }
    }

    suspend fun getWcongPoint(key : String, x : String, y : String) : Wcong {
        val deferred = viewModelScope.async {
             getWcong.invoke(key , x , y)
        }
        return deferred.await()
    }

}