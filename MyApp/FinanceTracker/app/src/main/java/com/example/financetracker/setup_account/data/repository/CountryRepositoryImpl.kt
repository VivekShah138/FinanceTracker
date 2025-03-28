package com.example.financetracker.setup_account.data.repository

import com.example.financetracker.setup_account.data.remote.CountryApi
import com.example.financetracker.setup_account.domain.model.Country
import com.example.financetracker.setup_account.domain.repository.CountryRepository

class CountryRepositoryImpl(
    private val api: CountryApi
): CountryRepository {
    override suspend fun getCountries(): List<Country> {
        return api.getCountries()
    }
}