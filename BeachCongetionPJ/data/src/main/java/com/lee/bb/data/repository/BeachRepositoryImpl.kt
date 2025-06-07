package com.lee.data.repository

import com.lee.bb.data.datasource.beach.BeachDataSource
import com.lee.data.datasource.preference.PreferenceDataSource
import com.lee.data.datasource.kakao.KakaoDataSource
import com.lee.domain.model.beach.BeachList
import com.lee.domain.model.kakao.KaKaoPoi
import com.lee.domain.model.kakao.Wcong
import com.lee.domain.repository.BeachRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BeachRepositoryImpl @Inject constructor(
    private val beachDataSource: BeachDataSource,
    private val kakaoDataSource: KakaoDataSource,
    private val dataStoreDataSource: PreferenceDataSource,
) : BeachRepository {
    /**
     * 해수욕장 혼잡도 가져오기
     * **/
    override suspend fun getBeachCongestion(): BeachList {
        return beachDataSource.getBeachCongestion()
    }

    /**
     * POI 리스트 가져오기
     * **/
    override suspend fun getKaKaoPoiList(key: String, keyword: String): KaKaoPoi {
        return kakaoDataSource.getKaKaoPoiList(key , keyword)
    }

    /**
     * Wcong 위 , 경도로 변환하기
     * **/
    override suspend fun getWcong(key: String, x: String, y: String): Wcong {
        return kakaoDataSource.getWcongLanLng(key , x , y)
    }

    /**
     * DataStore에서 현재 설정된 Navi 가져오기
     * **/
    override suspend fun getCurrentNavi(): Flow<String> {
        return dataStoreDataSource.getCurrentNavi()
    }

    /**
     * DataStore의 기본 Navi 설정하는 함수
     * **/
    override suspend fun setCurrentNavi(navi: String) {
        dataStoreDataSource.setCurrentNavi(navi)
    }

    /**
     * 위치 권한이 체크 되어있는지 여부를 가져오는 함수
     * **/
    override suspend fun getIsPermission(): Flow<Boolean> {
        return dataStoreDataSource.getIsPermission()
    }

    /**
     * 위치권한이 허용되어 있는지 여부에 대해 설정하는 함수
     * **/
    override suspend fun setIsPermission(permission: Boolean) {
        dataStoreDataSource.setIsPermission(permission)
    }
}