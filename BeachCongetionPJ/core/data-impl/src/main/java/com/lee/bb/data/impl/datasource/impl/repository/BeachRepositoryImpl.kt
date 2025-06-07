package com.lee.bb.data.impl.datasource.impl.repository

import com.lee.bb.data.dto.BeachListDTO
import com.lee.bb.data.repository.BeachRepository
import com.lee.bb.data.impl.datasource.impl.service.BeachApiService
import javax.inject.Inject

class BeachRepositoryImpl @Inject constructor(
    private val beachApiService: BeachApiService,
) : BeachRepository {
    override suspend fun getBeachCongestion(): BeachListDTO {
        return beachApiService.getBeachCongestion()
    }
}