package com.lee.beachcongetion.data.repository

import com.lee.beachcongetion.data.retrofit.BeachCongestionService

class BeachRepository {
    /**
     * Function for get Beach Congestion from model
     * **/
    suspend fun getBeachCongestion() = BeachCongestionService.getInstance().getBeachCongestion()
}