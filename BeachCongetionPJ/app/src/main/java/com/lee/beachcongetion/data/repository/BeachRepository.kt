package com.lee.beachcongetion.data.repository

import com.lee.beachcongetion.data.retrofit.BeachCongestionService
import com.lee.beachcongetion.data.retrofit.KakaoPoiInstance

class BeachRepository {
    /**
     * Function for get Beach Congestion from model
     * **/
    suspend fun getBeachCongestion() = BeachCongestionService.getInstance().getBeachCongestion()

    /**
     * Function for get Kakao Poi from model
     * **/
    suspend fun getKaKaoPoiList(key : String , keyword : String) = KakaoPoiInstance.getInstance().getPOIList(key , keyword)
}