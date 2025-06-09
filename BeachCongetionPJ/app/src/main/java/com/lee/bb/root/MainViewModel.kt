package com.lee.bb.root

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lee.bb.core.domain.beach.usecase.GetBeachCongestionListUseCase
import com.lee.bb.library.base.exts.runSuspendCatching
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBeachCongestionListUseCase: GetBeachCongestionListUseCase
) : ViewModel() {
    fun fetchBeachCongestion() {
        viewModelScope.launch {
            runSuspendCatching {
                getBeachCongestionListUseCase()
            }.onSuccess {
                Log.d("TAG", "fetchBeachCongestion: $it")
            }.onFailure {
                Log.d("TAG", "error" , it)
            }
        }
    }
}