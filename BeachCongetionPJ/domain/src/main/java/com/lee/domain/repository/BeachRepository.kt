package com.lee.domain.repository

import com.lee.domain.model.beach.BeachList
import com.lee.domain.model.kakao.KaKaoPoi
import com.lee.domain.model.kakao.Wcong
import com.lee.domain.model.kakao.WcongDocuments

interface BeachRepository {
    /**
     * 해수욕장 혼잡도 가져오기
     * **/
    suspend fun getBeachCongestion() : BeachList

    /**
     * POI 리스트 가져오기
     * **/
    suspend fun getKaKaoPoiList(key : String , keyword : String) : KaKaoPoi

    /**
     * Wcong 위 , 경도로 변환하기
     * **/
    suspend fun getWcong(key : String , x : String , y : String) : Wcong
}