package com.lee.bb.core.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

/**
 * Preference DataSource class
 * **/
class PreferenceDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = SETTINGS)

    private val currentNaviKey = stringPreferencesKey(CURRENT_NAVI)
    private val permissionKey = booleanPreferencesKey(IS_PERMISSION)

    val currentNavi: Flow<String> = context.dataStore.data
        .catch { exception ->
            when (exception) {
                is IOException -> emit(emptyPreferences())
            }
        }.map { preferences ->
            preferences[currentNaviKey] ?: NaviType.KAKAOMAP.name
        }

    suspend fun setCurrentNavi(navi: String) {
        context.dataStore.edit { preferences ->
            preferences[currentNaviKey] = navi
        }
    }

    val isPermission: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            when (exception) {
                is IOException -> emit(emptyPreferences())
            }
        }.map { preferences ->
            preferences[permissionKey] ?: false
        }

    suspend fun setIsPermission(permission: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[permissionKey] = permission
        }
    }

    companion object {
        const val CURRENT_NAVI = "current_navi"
        const val IS_PERMISSION = "is_permission"
        const val SETTINGS = "settings"

        enum class NaviType {
            KAKAOMAP , TMAP
        }
    }
}