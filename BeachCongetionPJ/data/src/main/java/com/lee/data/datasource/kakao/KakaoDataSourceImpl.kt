package com.lee.data.datasource.kakao

import com.lee.data.api.KakaoMapApi
import com.lee.data.mapper.KakaoMapper
import com.lee.domain.model.kakao.KaKaoPoi
import com.lee.domain.model.kakao.Wcong
import javax.inject.Inject

class KakaoDataSourceImpl @Inject constructor(
    private val kakaoMapApi: KakaoMapApi
) : KakaoDataSource {
    override suspend fun getKaKaoPoiList(key: String, keyword: String): KaKaoPoi {
        return KakaoMapper.mapperToKakaoPoi(kakaoMapApi.getPOIList(key , keyword))
    }

    override suspend fun getWcongLanLng(key: String, x: String, y: String): Wcong {
        return KakaoMapper.mapperToWcong(kakaoMapApi.getWcongPoint(key , x , y))
    }
}