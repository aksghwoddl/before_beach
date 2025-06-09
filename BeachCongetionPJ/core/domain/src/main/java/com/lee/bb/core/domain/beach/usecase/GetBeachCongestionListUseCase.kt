package com.lee.bb.core.domain.beach.usecase

import com.lee.bb.core.data.repository.BeachRepository
import com.lee.bb.core.domain.beach.mapper.toBeachList
import com.lee.bb.core.domain.beach.model.Beach
import javax.inject.Inject

class GetBeachCongestionListUseCase @Inject constructor(
    private val beachRepository: BeachRepository
) {
    suspend operator fun invoke(): List<Beach> {
        return beachRepository.getBeachCongestion().toBeachList()
    }
}