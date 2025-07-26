package com.example.financetracker.data.data_source.remote

import com.example.financetracker.domain.model.CurrencyResponse
import com.example.financetracker.utils.ApiClient
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyRatesApi {

    @GET("{apiKey}/latest/{baseCurrency}")
    suspend fun getExchangeRates(
        @Path("apiKey") apiKey: String = ApiClient.API_KEY,  // Pass the API key dynamically
        @Path("baseCurrency") baseCurrency: String
    ): CurrencyResponse
}