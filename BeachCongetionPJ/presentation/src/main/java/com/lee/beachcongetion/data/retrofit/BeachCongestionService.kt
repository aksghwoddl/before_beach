package com.lee.beachcongetion.data.retrofit

import com.lee.beachcongetion.common.BEACH_CONGESTION_SUB_URL
import com.lee.beachcongetion.common.BEACH_CONGESTION_URL
import com.lee.beachcongetion.data.retrofit.model.beach.BeachCongestionList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BeachCongestionService {
    @GET(BEACH_CONGESTION_SUB_URL)
    suspend fun getBeachCongestion() : Response<BeachCongestionList>

    companion object{
        private lateinit var instance : BeachCongestionService

        fun getInstance() : BeachCongestionService {
            if(!Companion::instance.isInitialized){
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