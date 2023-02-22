package com.lee.data.api

import com.lee.data.common.DataUtils
import com.lee.data.model.beach.BeachListDTO
import retrofit2.http.GET

interface BeachApi {
    @GET(DataUtils.GET_BEACH_CONGESTION_URL)
    suspend fun getBeachCongestion() : BeachListDTO
}