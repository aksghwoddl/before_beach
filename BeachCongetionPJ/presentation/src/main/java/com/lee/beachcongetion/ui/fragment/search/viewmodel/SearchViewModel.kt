package com.lee.beachcongetion.ui.fragment.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lee.beachcongetion.R
import com.lee.beachcongetion.common.ResourceProvider
import com.lee.domain.model.beach.Beach
import com.lee.domain.model.beach.BeachList
import com.lee.domain.model.kakao.Documents
import com.lee.domain.model.kakao.KaKaoPoi
import com.lee.domain.usecase.GetCurrentNavi
import com.lee.domain.usecase.GetKakaoPoi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getKakaoPoi: GetKakaoPoi ,
    private val getCurrentNavi: GetCurrentNavi ,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _beachList = MutableLiveData<BeachList>()
    val beachList : LiveData<BeachList>
    get() = _beachList
    fun setBeachList(list : BeachList){
        _beachList.value = list
    }

    private val _poiList = MutableLiveData<KaKaoPoi>()
    val poiList : LiveData<KaKaoPoi>
    get() = _poiList

    private val _destination = MutableLiveData<Documents>()
    val destination : LiveData<Documents>
    get() = _destination

    private val _searchList = MutableLiveData<ArrayList<Beach>>()
    val searchList : LiveData<ArrayList<Beach>>
    get() = _searchList
    fun setSearchList(list : ArrayList<Beach>){
        _searchList.value = list
    }

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage : LiveData<String>
        get() = _toastMessage
    fun setToastMessage(message : String){
        _toastMessage.value = message
    }

    private val _currentNavi = MutableLiveData<String>()
    val currentNavi : LiveData<String>
    get() = _currentNavi

    private val exceptionHandler = CoroutineExceptionHandler { _ , exception ->
        when(exception) {
            is SocketTimeoutException -> { setToastMessage(resourceProvider.getString(R.string.socket_timeout_exception))}
        }
    }

    /**
     * 선택된 해변의 POI 정보 불러오기 (isNavi - 길찾기 버튼 클릭 여부)
     * **/
    fun getKakaoPoiList(key : String , keyword : String , isNavi : Boolean) {
        viewModelScope.launch(exceptionHandler) {
            val kakaoPoi = getKakaoPoi.invoke(key , keyword)
            if(isNavi){ // 길찾기로 인해 호출 되었을떼
                _destination.value = kakaoPoi.documents[0]
            } else { // 길찾기로 인해 호출되지 않았을때
                _poiList.value = kakaoPoi
            }
        }
    }

    /**
     * 현재 설정된 기본 네비게이션을 불러오는 함수
     * **/
    fun getSelectedNavi() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getCurrentNavi.invoke()
            }.collect{
                _currentNavi.value = it
            }
        }
    }

}