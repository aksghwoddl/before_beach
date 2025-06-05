package com.lee.data.service

import com.lee.data.common.DataUtils
import com.lee.data.model.beach.BeachListDTO
import retrofit2.http.GET

interface BeachApiService {
    @GET(DataUtils.GET_BEACH_CONGESTION_URL)
    suspend fun getBeachCongestion() : BeachListDTO
}