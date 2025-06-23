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
    const val API_KEY = "21f5e9959aaff358c6ed28bc"
}
