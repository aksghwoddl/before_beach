package com.lee.bb.data.impl.datasource.impl.datasource

import com.lee.bb.data.datasource.preference.PreferenceDataSource
import com.lee.bb.data.datastore.PreferenceDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferenceDataSourceImpl @Inject constructor(
    private val dataStorePreference: PreferenceDataStore
) : PreferenceDataSource {
    override suspend fun getCurrentNavi(): Flow<String> {
        return dataStorePreference.currentNavi
    }

    override suspend fun setCurrentNavi(navi: String) {
        dataStorePreference.setCurrentNavi(navi)
    }

    override suspend fun getIsPermission(): Flow<Boolean> {
        return dataStorePreference.isPermission
    }

    override suspend fun setIsPermission(permission: Boolean) {
        dataStorePreference.setIsPermission(permission)
    }
}