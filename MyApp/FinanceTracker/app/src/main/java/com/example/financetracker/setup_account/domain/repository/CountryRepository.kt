package com.example.financetracker.setup_account.domain.repository

import com.example.financetracker.setup_account.domain.model.Country

interface CountryRepository {
    suspend fun getCountries(): List<Country>
}