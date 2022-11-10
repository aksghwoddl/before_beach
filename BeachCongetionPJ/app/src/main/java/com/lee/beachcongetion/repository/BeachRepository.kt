package com.lee.beachcongetion.repository

import com.lee.beachcongetion.retrofit.BeachCongestionService

class BeachRepository {
    /**
     * Function for get Beach Congestion from model
     * **/
    suspend fun getBeachCongestion() = BeachCongestionService.getInstance().getBeachCongestion()
}