package com.lee.domain.usecase

import com.lee.domain.repository.BeachRepository
import javax.inject.Inject

class SetIsPermission @Inject constructor(
    private val repository: BeachRepository
) {
    suspend fun invoke(permission : Boolean) {
        repository.setIsPermission(permission)
    }
}