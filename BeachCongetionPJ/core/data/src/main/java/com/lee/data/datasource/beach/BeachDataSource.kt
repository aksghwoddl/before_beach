package com.lee.data.datasource.beach

import com.lee.domain.model.beach.BeachList

interface BeachDataSource {
    suspend fun getBeachCongestion() : BeachList
}