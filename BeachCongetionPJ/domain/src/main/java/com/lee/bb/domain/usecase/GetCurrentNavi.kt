package com.lee.domain.usecase

import com.lee.domain.repository.BeachRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentNavi @Inject constructor(
    private val repository: BeachRepository
) {
    suspend fun invoke() : Flow<String>{
        return repository.getCurrentNavi()
    }
}