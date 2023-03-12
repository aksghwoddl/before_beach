package com.lee.data.datasource.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreDataSource {
    suspend fun getCurrentNavi() : Flow<String>
    suspend fun setCurrentNavi(navi : String)
}