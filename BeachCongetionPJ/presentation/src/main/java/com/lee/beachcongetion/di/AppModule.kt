package com.lee.beachcongetion.di

import com.lee.beachcongetion.common.BEACH_BASE_URL
import com.lee.beachcongetion.common.CONNECTION_TIMEOUT
import com.lee.beachcongetion.common.KAKAO_BASE_URL
import com.lee.data.api.BeachApi
import com.lee.data.api.KakaoMapApi
import com.lee.domain.model.kakao.CurrentLatLng
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
class AppModule {
    /**
     * OkHttpClient를 provide하는 함수
     * **/
    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level= HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideKaKaoApi(okHttpClient: OkHttpClient) : KakaoMapApi {
        return Retrofit.Builder()
            .baseUrl(KAKAO_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KakaoMapApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBeachApi(okHttpClient: OkHttpClient) : BeachApi {
        return Retrofit.Builder()
            .baseUrl(BEACH_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BeachApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrentLatLng() : CurrentLatLng {
        return CurrentLatLng()
    }
}