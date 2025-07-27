package com.example.financetracker.domain.repository.local

import com.example.financetracker.domain.model.Country

interface CountryLocalRepository {

    suspend fun getCountries(): List<Country>
    suspend fun insertCountries()
    suspend fun insertCountries(countries: List<Country>)

}