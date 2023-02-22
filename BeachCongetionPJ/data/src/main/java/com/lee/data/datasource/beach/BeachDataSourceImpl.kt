package com.lee.data.datasource.beach

import com.lee.data.api.BeachApi
import com.lee.data.mapper.BeachMapper
import com.lee.domain.model.beach.BeachList
import javax.inject.Inject

class BeachDataSourceImpl @Inject constructor(
    private val beachApi: BeachApi
) : BeachDataSource {
    override suspend fun getBeachCongestion(): BeachList {
        return BeachMapper.mapperToBeachList(beachApi.getBeachCongestion())
    }
}