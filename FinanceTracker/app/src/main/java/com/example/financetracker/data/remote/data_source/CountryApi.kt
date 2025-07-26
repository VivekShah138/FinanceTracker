package com.example.financetracker.data.remote.data_source

import com.example.financetracker.setup_account.domain.model.Country
import retrofit2.http.GET

interface CountryApi {

    @GET("v3.1/all")
    suspend fun getCountries(): List<Country>
}