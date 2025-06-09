package com.lee.bb.core.data.impl.di

import com.lee.bb.core.data.datasource.preference.PreferenceDataSource
import com.lee.bb.core.data.impl.datasource.PreferenceDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindPreferenceDataSource(preferenceDataSourceImpl: PreferenceDataSourceImpl): PreferenceDataSource
}