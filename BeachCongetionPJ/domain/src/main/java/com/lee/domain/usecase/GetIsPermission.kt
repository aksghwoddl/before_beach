package com.lee.domain.usecase

import com.lee.domain.repository.BeachRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetIsPermission @Inject constructor(
    private val repository: BeachRepository
) {
    suspend fun invoke() : Flow<Boolean> {
        return repository.getIsPermission()
    }
}