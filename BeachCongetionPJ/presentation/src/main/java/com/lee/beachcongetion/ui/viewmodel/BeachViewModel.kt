package com.lee.beachcongetion.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lee.beachcongetion.data.repository.BeachRepository
import com.lee.beachcongetion.data.retrofit.model.beach.BeachCongestionModel
import com.lee.beachcongetion.data.retrofit.model.kakao.Documents
import com.lee.beachcongetion.data.retrofit.model.kakao.KakaoPoiModel
import com.lee.beachcongetion.data.retrofit.model.kakao.WcongModel
import kotlinx.coroutines.*

class BeachViewModel(private val repository: BeachRepository) : ViewModel() {
    val beachList = MutableLiveData<MutableList<BeachCongestionModel>>()
    var progressVisible = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()
    var poiList = MutableLiveData<MutableList<Documents>>()
    private var job : Job? = null

    private val exceptionHandler = CoroutineExceptionHandler{
        _ , throwExceptionHandler -> throwExceptionHandler.localizedMessage?.let { onError(it) }
    }

    /**
     * Get Beach Congestion List from repository
     * **/

    fun getAllBeachCongestion() {
        progressVisible.postValue(true)
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            val response = repository.getBeachCongestion()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    beachList.postValue(response.body()?.getAllBeachList())
                    progressVisible.postValue(false)
                } else {
                    onError(response.message())
                }
            }
        }
    }

    fun getKakaoPoiList(key : String , keyword : String) {
        progressVisible.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getKaKaoPoiList(key, keyword)
            CoroutineScope(Dispatchers.Main).launch {
                if(response.isSuccessful){
                    poiList.value = response.body()?.documents
                } else {
                    onError("위치정보를 가져오는중 문제가 발생했습니다.")
                }
                progressVisible.value = false
            }
        }
    }

    suspend fun getWcongPoint(key : String, x : String, y : String) : WcongModel? {
        val deferred = CoroutineScope(Dispatchers.IO).async {
            val response = repository.getWcongLanLng(key , x, y)
            if(response.isSuccessful){
                response.body()!!
            } else {
                onError("좌표를 변환하는중 오류가 발생했습니다.")
                null
            }
        }
        return deferred.await()
    }

    private fun onError(message : String) {
        errorMessage.postValue(message)
        progressVisible.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}