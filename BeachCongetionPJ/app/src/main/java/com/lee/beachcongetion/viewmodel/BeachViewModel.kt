package com.lee.beachcongetion.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lee.beachcongetion.repository.BeachRepository
import com.lee.beachcongetion.retrofit.model.BeachCongestionModel
import kotlinx.coroutines.*

class BeachViewModel(private val repository: BeachRepository) : ViewModel() {
    val beachList = MutableLiveData<MutableList<BeachCongestionModel>>()
    var progressVisible = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()
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

    private fun onError(message : String) {
        errorMessage.postValue(message)
        progressVisible.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}