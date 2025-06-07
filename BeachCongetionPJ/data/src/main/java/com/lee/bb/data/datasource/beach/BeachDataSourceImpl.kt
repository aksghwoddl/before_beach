package com.lee.data.datasource.beach

import com.lee.bb.data.datasource.beach.BeachDataSource
import com.lee.data.service.BeachApiService
import com.lee.data.mapper.BeachMapper
import com.lee.domain.model.beach.BeachList
import javax.inject.Inject

class BeachDataSourceImpl @Inject constructor(
    private val beachApiService: BeachApiService
) : BeachDataSource {
    override suspend fun getBeachCongestion(): BeachList {
        return BeachMapper.mapperToBeachList(beachApiService.getBeachCongestion())
    }
}