package com.lee.data.datasource.datastore

import com.lee.data.datastore.DataStoreModule
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreDataSourceImpl @Inject constructor(
    private val dataStoreModule: DataStoreModule
) : DataStoreDataSource {
    override suspend fun getCurrentNavi(): Flow<String> {
        return dataStoreModule.currentNavi
    }

    override suspend fun setCurrentNavi(navi: String) {
        dataStoreModule.setCurrentNavi(navi)
    }
}