package com.xardev.flightnow.di.modules

import com.xardev.flightnow.data.remote.ApiService
import com.xardev.flightnow.repositories.MainRepository
import com.xardev.flightnow.repositories.MainRepositoryImpl
import com.xardev.flightnow.utils.BaseSchedulers
import com.xardev.flightnow.utils.Constants
import com.xardev.flightnow.utils.StandardSchedulers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


@Module
@InstallIn(SingletonComponent::class)
class SchedulersModule {

    @Provides
    fun provideStandardSchedulers(): BaseSchedulers {
        return StandardSchedulers()
    }

}