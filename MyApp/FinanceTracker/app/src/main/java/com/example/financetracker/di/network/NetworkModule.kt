package com.example.financetracker.di

import com.example.financetracker.setup_account.data.remote.ApiClient
import com.example.financetracker.setup_account.data.remote.CountryApi
import com.example.financetracker.setup_account.data.remote.CurrencyRatesApi
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
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .protocols(listOf(okhttp3.Protocol.HTTP_1_1))
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL_COUNTRY) // Use the base URL from ApiClient
            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideCountryApi(retrofit: Retrofit): CountryApi {
        return retrofit.create(CountryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyRatesApi(): CurrencyRatesApi {
        return Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL_CURRENCY_RATES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyRatesApi::class.java)
    }
}
