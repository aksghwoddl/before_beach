package com.lee.beachcongetion.retrofit

import com.lee.beachcongetion.common.BEACH_CONGESTION_URL
import com.lee.beachcongetion.retrofit.model.BeachCongestionModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BeachCongestionService {
    @GET("/seantour_map/travel/ getBeachCongestionApi.do")
    suspend fun getBeachCongestion() : Response<List<BeachCongestionModel>>

    companion object{
        private lateinit var instance : BeachCongestionService

        fun getInstance() : BeachCongestionService{
            if(!::instance.isInitialized){
                val retrofit = Retrofit.Builder()
                    .baseUrl(BEACH_CONGESTION_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                instance =  retrofit.create(BeachCongestionService::class.java)
            }
            return instance
        }
    }
}