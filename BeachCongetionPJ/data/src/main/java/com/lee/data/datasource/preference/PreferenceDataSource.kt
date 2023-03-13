package com.lee.data.datasource.preference

import kotlinx.coroutines.flow.Flow

interface PreferenceDataSource {
    suspend fun getCurrentNavi() : Flow<String>
    suspend fun setCurrentNavi(navi : String)
}