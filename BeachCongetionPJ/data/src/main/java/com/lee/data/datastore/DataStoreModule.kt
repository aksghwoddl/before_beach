package com.lee.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lee.data.common.DataUtils
import com.lee.data.common.Navi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

/**
 * Preference DataSource class
 * **/
class DataStoreModule @Inject constructor(
    @ApplicationContext private val context : Context
) {
    private val Context.dataStore by preferencesDataStore(name = DataUtils.SETTINGS)

    private val currentNaviKey = stringPreferencesKey(DataUtils.CURRENT_NAVI)
    private val permissionKey = booleanPreferencesKey(DataUtils.IS_PERMISSION)

    val currentNavi :Flow<String> = context.dataStore.data
        .catch{ exception ->
            when(exception){
                is IOException -> emit(emptyPreferences())
            }
        }.map { preferences ->
            preferences[currentNaviKey] ?: Navi.KAKAO_MAP.name
        }

    suspend fun setCurrentNavi(navi : String){
        context.dataStore.edit { preferences ->
            preferences[currentNaviKey] = navi
        }
    }

    val isPermission :Flow<Boolean> = context.dataStore.data
        .catch{ exception ->
            when(exception){
                is IOException -> emit(emptyPreferences())
            }
        }.map { preferences ->
            preferences[permissionKey] ?: false
        }

    suspend fun setIsPermission(permission : Boolean){
        context.dataStore.edit { preferences ->
            preferences[permissionKey] = permission
        }
    }
}