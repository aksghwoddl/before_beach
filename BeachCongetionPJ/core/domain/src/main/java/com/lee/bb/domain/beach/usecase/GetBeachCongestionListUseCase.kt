package com.lee.bb.domain.beach.usecase

import com.lee.bb.data.repository.BeachRepository
import com.lee.bb.domain.beach.mapper.toBeachList
import com.lee.bb.domain.beach.model.Beach
import javax.inject.Inject

class GetBeachCongestionListUseCase @Inject constructor(
    private val beachRepository: BeachRepository
) {
    suspend operator fun invoke(): List<Beach> {
        return beachRepository.getBeachCongestion().toBeachList()
    }
}