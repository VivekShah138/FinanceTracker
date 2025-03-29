package com.example.financetracker.setup_account.domain.repository.remote

import com.example.financetracker.setup_account.domain.model.Country

interface CountryRemoteRepository {
    suspend fun getCountries(): List<Country>
}