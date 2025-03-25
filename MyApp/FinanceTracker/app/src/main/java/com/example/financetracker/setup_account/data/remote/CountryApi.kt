package com.example.financetracker.setup_account.data.remote

import com.example.financetracker.setup_account.domain.model.Country
import retrofit2.http.GET

interface CountryApi {

    @GET("v3.1/all")
    suspend fun getCountries(): List<Country>
}