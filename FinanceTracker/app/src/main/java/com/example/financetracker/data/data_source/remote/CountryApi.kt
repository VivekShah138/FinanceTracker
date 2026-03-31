package com.example.financetracker.data.data_source.remote

import com.example.financetracker.domain.model.Country
import retrofit2.http.GET

interface CountryApi {

    @GET("v3.1/all")
    suspend fun getCountries(): List<Country>
}