package com.example.financetracker.setup_account.data.remote

import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    const val BASE_URL_COUNTRY = "https://restcountries.com/"
    const val BASE_URL_CURRENCY_RATES = "https://v6.exchangerate-api.com/v6/"
    const val API_KEY = "21f5e9959aaff358c6ed28bc"  // Replace with your actual API key

//    private val okHttpClient: OkHttpClient by lazy {
//        OkHttpClient.Builder()
//            .connectTimeout(15, TimeUnit.SECONDS)
//            .readTimeout(20, TimeUnit.SECONDS)
//            .writeTimeout(20, TimeUnit.SECONDS)
//            .retryOnConnectionFailure(true)
//            .protocols(listOf(Protocol.HTTP_1_1)) // ⬅ Force HTTP/1.1
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            })
//            .build()
//    }
//
//
//    val instance: CountryApi by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL_COUNTRY)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//            .create(CountryApi::class.java)
//    }
//
//    val instance2: CurrencyRatesApi by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL_CURRENCY_RATES)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(CurrencyRatesApi::class.java)
//    }

//    fun getApiKey() = API_KEY  // You can create a function to retrieve the API key if needed

}
