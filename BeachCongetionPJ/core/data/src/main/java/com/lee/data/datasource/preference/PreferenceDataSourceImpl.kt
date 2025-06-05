package com.lee.data.datasource.preference

import com.lee.data.datastore.DataStoreModule
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferenceDataSourceImpl @Inject constructor(
    private val dataStoreModule: DataStoreModule
) : PreferenceDataSource {
    override suspend fun getCurrentNavi(): Flow<String> {
        return dataStoreModule.currentNavi
    }

    override suspend fun setCurrentNavi(navi: String) {
        dataStoreModule.setCurrentNavi(navi)
    }

    override suspend fun getIsPermission(): Flow<Boolean> {
        return dataStoreModule.isPermission
    }

    override suspend fun setIsPermission(permission: Boolean) {
        dataStoreModule.setIsPermission(permission)
    }
}