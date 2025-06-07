package com.lee.beachcongetion.di

import com.lee.bb.data.datasource.beach.BeachDataSource
import com.lee.data.datasource.beach.BeachDataSourceImpl
import com.lee.data.datasource.kakao.KakaoDataSource
import com.lee.data.datasource.kakao.KakaoDataSourceImpl
import com.lee.data.datasource.preference.PreferenceDataSource
import com.lee.data.datasource.preference.PreferenceDataSourceImpl
import com.lee.data.repository.BeachRepositoryImpl
import com.lee.domain.repository.BeachRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {
    /**
     * Repository bind하는 함수
     * **/
    @Binds
    @Singleton
    abstract fun bindBeachRepository(beachRepositoryImpl: BeachRepositoryImpl) : BeachRepository

    /**
     * BeachDataSource bind하는 함수
     * **/
    @Binds
    @Singleton
    abstract fun bindKBeachDataSource(beachDataSourceImpl: BeachDataSourceImpl) : BeachDataSource

    /**
     * KakaoDataSource bind하는 함수
     * **/
    @Binds
    @Singleton
    abstract fun bindKakaoDataSource(kakaoDataSourceImpl: KakaoDataSourceImpl) : KakaoDataSource

    /**
     * DataSource를 bind하는 함수
     * **/
    @Binds
    @Singleton
    abstract fun bindDataStoreDataSource(dataStoreDataSourceImpl: PreferenceDataSourceImpl) : PreferenceDataSource
}