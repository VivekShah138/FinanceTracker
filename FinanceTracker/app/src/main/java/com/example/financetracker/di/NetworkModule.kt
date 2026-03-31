package com.example.financetracker.di

import com.example.financetracker.data.data_source.remote.CountryApi
import com.example.financetracker.data.data_source.remote.CurrencyRatesApi
import com.example.financetracker.utils.ApiClient
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
    fun provideCountryApi(): CountryApi {
        return Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL_COUNTRY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryApi::class.java)
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
