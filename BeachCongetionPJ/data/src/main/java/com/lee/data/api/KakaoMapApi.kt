package com.lee.data.api

import com.lee.data.common.DataUtils
import com.lee.data.model.kakao.KaKaoPoiDTO
import com.lee.data.model.kakao.WcongDTO
import com.lee.domain.model.kakao.KaKaoPoi
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoMapApi {
    @GET(DataUtils.KAKAO_POI_URL)
    suspend fun getPOIList(
        @Header(DataUtils.AUTHORIZATION) key : String ,
        @Query("query") keyword : String ,
    ) : KaKaoPoiDTO

    @GET(DataUtils.KAKAO_CONVERT_WCONG_URL)
    suspend fun getWcongPoint(
        @Header(DataUtils.AUTHORIZATION) key : String ,
        @Query("x") longitude : String ,
        @Query("y") latitude : String
    ) : WcongDTO
}