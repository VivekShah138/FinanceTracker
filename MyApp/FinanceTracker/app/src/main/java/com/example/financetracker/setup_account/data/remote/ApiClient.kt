package com.example.financetracker.setup_account.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL_COUNTRY = "https://restcountries.com/"
    private const val BASE_URL_CURRENCY_RATES = "https://v6.exchangerate-api.com/v6/"
    private const val API_KEY = "21f5e9959aaff358c6ed28bc"  // Replace with your actual API key

    val instance: CountryApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_COUNTRY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryApi::class.java)
    }

    val instance2: CurrencyRatesApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_CURRENCY_RATES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyRatesApi::class.java)
    }

    fun getApiKey() = API_KEY  // You can create a function to retrieve the API key if needed

}
