package com.lee.beachcongetion.data.retrofit

import com.lee.beachcongetion.common.*
import com.lee.beachcongetion.data.retrofit.model.kakao.KakaoPoiModel
import com.lee.beachcongetion.data.retrofit.model.kakao.WcongModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoPoiService {
    @GET(KAKAO_POI_URL)
    suspend fun getPOIList(
        @Header(AUTHORIZATION) key : String ,
        @Query("query") keyword : String ,
    ) : Response<KakaoPoiModel>

    @GET(KAKAO_CONVERT_WCONG)
    suspend fun getWcongPoint(
        @Header(AUTHORIZATION) key : String ,
        @Query("x") longitude : String ,
        @Query("y") latitude : String
    ) : Response<WcongModel>
}

class KakaoPoiInstance {
    companion object{
        private lateinit var instance : KakaoPoiService
        fun getInstance() : KakaoPoiService {
            if(!::instance.isInitialized){
                val retrofit = Retrofit.Builder()
                    .baseUrl(KAKAO_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                instance = retrofit.create(KakaoPoiService::class.java)
            }
            return instance
        }
    }
}