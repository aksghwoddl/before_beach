package com.lee.domain.usecase

import com.lee.domain.repository.BeachRepository
import javax.inject.Inject

class SetCurrentNavi @Inject constructor(
    private val repository: BeachRepository
) {
    suspend fun invoke(navi : String) {
        repository.setCurrentNavi(navi)
    }
}