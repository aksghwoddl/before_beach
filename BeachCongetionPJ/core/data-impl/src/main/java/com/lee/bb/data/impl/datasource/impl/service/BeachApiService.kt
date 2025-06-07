package com.lee.data.impl.service

import com.lee.bb.data.common.DataConst
import com.lee.bb.data.dto.BeachListDTO
import retrofit2.http.GET

interface BeachApiService {
    @GET(DataConst.GET_BEACH_CONGESTION_URL)
    suspend fun getBeachCongestion() : BeachListDTO
}