package com.lee.domain.repository

import com.lee.domain.model.beach.BeachList
import com.lee.domain.model.kakao.KaKaoPoi
import com.lee.domain.model.kakao.Wcong
import kotlinx.coroutines.flow.Flow

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

    /**
     * DataStore에서 현재 설정된 Navi 가져오기
     * **/
    suspend fun getCurrentNavi() : Flow<String>

    /**
     * DataStore의 기본 Navi 설정하는 함수
     * **/
    suspend fun setCurrentNavi(navi : String)

    /**
     * 위치 권한이 체크 되어있는지 여부를 가져오는 함수
     * **/
    suspend fun getIsPermission() : Flow<Boolean>

    /**
     * 위치권한이 허용되어 있는지 여부에 대해 설정하는 함수
     * **/
    suspend fun setIsPermission(permission: Boolean)
}