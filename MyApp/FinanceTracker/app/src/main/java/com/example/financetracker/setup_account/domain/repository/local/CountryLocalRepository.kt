package com.example.financetracker.setup_account.domain.repository.local

import com.example.financetracker.setup_account.domain.model.Country

interface CountryLocalRepository {

    suspend fun getCountries(): List<Country>
    suspend fun insertCountries()
    suspend fun insertCountries(countries: List<Country>)

}