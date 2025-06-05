package com.lee.domain.usecase

import com.lee.domain.model.kakao.Wcong
import com.lee.domain.repository.BeachRepository
import javax.inject.Inject

class GetWcong @Inject constructor(
    private val repository: BeachRepository
){
    suspend fun invoke(key : String , x : String , y : String) : Wcong {
        return repository.getWcong(key ,x , y)
    }
}