package com.example.financetracker.data.remote.repository

import com.example.financetracker.data.remote.data_source.CountryApi
import com.example.financetracker.setup_account.domain.model.Country
import com.example.financetracker.setup_account.domain.repository.remote.CountryRemoteRepository

class CountryRemoteRepositoryImpl(
    private val api: CountryApi
): CountryRemoteRepository {
    override suspend fun getCountries(): List<Country> {
        return api.getCountries()
    }
}