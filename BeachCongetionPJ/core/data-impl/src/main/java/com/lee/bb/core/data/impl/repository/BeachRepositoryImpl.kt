package com.lee.bb.core.data.impl.repository

import com.lee.bb.core.data.dto.BeachListDTO
import com.lee.bb.core.data.repository.BeachRepository
import com.lee.bb.core.data.impl.service.BeachApiService
import javax.inject.Inject

class BeachRepositoryImpl @Inject constructor(
    private val beachApiService: BeachApiService,
) : BeachRepository {
    override suspend fun getBeachCongestion(): BeachListDTO {
        return beachApiService.getBeachCongestion()
    }
}