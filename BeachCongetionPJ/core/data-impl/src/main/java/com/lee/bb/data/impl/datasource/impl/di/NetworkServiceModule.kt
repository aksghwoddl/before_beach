package com.lee.bb.data.impl.datasource.impl.di

import com.lee.bb.data.common.DataConst
import com.lee.data.impl.service.BeachApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkServiceModule {
    @Provides
    @Singleton
    fun provideBeachApiService(
        okHttpClient: OkHttpClient,
    ): BeachApiService = Retrofit.Builder()
        .baseUrl(DataConst.GET_BEACH_CONGESTION_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BeachApiService::class.java)

    /**
     * OkHttpClient를 provide하는 함수
     * **/
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .build()
    }
}