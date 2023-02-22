package com.lee.data.datasource.kakao

import com.lee.domain.model.kakao.KaKaoPoi
import com.lee.domain.model.kakao.Wcong

interface KakaoDataSource {
    suspend fun getKaKaoPoiList(key : String , keyword : String) : KaKaoPoi

    suspend fun getWcongLanLng(key : String , x : String , y : String) : Wcong
}