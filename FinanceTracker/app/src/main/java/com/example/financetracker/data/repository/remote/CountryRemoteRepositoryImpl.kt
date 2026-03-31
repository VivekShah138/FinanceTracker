package com.example.financetracker.data.repository.remote

import com.example.financetracker.data.data_source.remote.CountryApi
import com.example.financetracker.domain.model.Country
import com.example.financetracker.domain.repository.remote.CountryRemoteRepository

class CountryRemoteRepositoryImpl(
    private val api: CountryApi
): CountryRemoteRepository {
    override suspend fun getCountries(): List<Country> {
        return api.getCountries()
    }
}