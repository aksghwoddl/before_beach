package com.lee.bb.data.datasource.preference

import kotlinx.coroutines.flow.Flow

interface PreferenceDataSource {
    suspend fun getCurrentNavi() : Flow<String>
    suspend fun setCurrentNavi(navi : String)
    suspend fun getIsPermission() : Flow<Boolean>
    suspend fun setIsPermission(permission : Boolean)
}