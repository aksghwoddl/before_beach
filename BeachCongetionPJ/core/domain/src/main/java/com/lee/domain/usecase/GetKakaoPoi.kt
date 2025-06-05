package com.lee.domain.usecase

import com.lee.domain.model.kakao.KaKaoPoi
import com.lee.domain.repository.BeachRepository
import javax.inject.Inject

class GetKakaoPoi @Inject constructor(
    private val repository: BeachRepository
) {
    suspend fun invoke(key : String , keyword : String) : KaKaoPoi {
        return repository.getKaKaoPoiList(key , keyword)
    }
}