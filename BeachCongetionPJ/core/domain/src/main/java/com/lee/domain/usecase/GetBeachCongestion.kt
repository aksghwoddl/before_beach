package com.lee.domain.usecase

import com.lee.domain.model.beach.BeachList
import com.lee.domain.repository.BeachRepository
import javax.inject.Inject

class GetBeachCongestion @Inject constructor(
    private val repository: BeachRepository
) {
    suspend fun invoke() : BeachList{
        return repository.getBeachCongestion()
    }
}