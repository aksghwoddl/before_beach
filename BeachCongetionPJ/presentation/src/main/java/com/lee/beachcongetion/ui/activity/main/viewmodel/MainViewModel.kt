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
import com.lee.domain.usecase.GetWcong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBeachCongestion: GetBeachCongestion ,
    private val getWcong: GetWcong
) : ViewModel() {
    private val _beachList = MutableLiveData<BeachList>()
    val beachList : LiveData<BeachList>
    get() = _beachList

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
    /**
     * 현재 Location 객체를 담는 LiveData를 setting하는 함수
     * 1. location = setting할 위치 객체
     * 2. requestFromCurrentButton = 현재 위치 버튼으로부터 호출되었는지 확인하는 파라미터
     * **/
    fun setCurrentLocation(location : Location , requestFromCurrentButton : Boolean){
        _currentLocation.value = location
        _requestCurrentButton.value = requestFromCurrentButton
    }

    private val _currentLatLng = MutableLiveData<CurrentLatLng>()
    val currentLatLng : LiveData<CurrentLatLng>
    get() = _currentLatLng
    fun setCurrentLatLng(latLng: CurrentLatLng) {
        _currentLatLng.value = latLng
    }

    private val _requestCurrentButton = MutableLiveData<Boolean>()
    val requestCurrentButton : LiveData<Boolean>
    get() = _requestCurrentButton

    private val exceptionHandler = CoroutineExceptionHandler{
        _ , throwExceptionHandler -> throwExceptionHandler.localizedMessage?.let { _toastMessage.value = it }
    }

    /**
     * 해수욕장 혼잡도 불러오기
     * **/
    fun getAllBeachCongestion() {
        viewModelScope.launch(exceptionHandler) {
            val beachList = getBeachCongestion.invoke()
            _beachList.value = beachList
        }
    }

    /**
     * 전달받은 좌표를 Wcong 좌표계로 변환해주는 함수
     * **/
    suspend fun getWcongPoint(key : String, x : String, y : String) : Wcong {
        val deferred = viewModelScope.async {
             getWcong.invoke(key , x , y)
        }
        return deferred.await()
    }

}