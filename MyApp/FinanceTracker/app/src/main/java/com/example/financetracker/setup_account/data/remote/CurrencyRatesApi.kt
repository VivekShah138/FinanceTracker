package com.example.financetracker.setup_account.data.remote

import com.example.financetracker.setup_account.domain.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyRatesApi {

    @GET("{apiKey}/latest/{baseCurrency}")
    suspend fun getExchangeRates(
        @Path("apiKey") apiKey: String = ApiClient.getApiKey(),  // Pass the API key dynamically
        @Path("baseCurrency") baseCurrency: String
    ): CurrencyResponse
}