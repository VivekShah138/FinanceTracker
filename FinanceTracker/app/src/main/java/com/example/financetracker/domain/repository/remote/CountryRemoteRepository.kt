package com.example.financetracker.domain.repository.remote

import com.example.financetracker.domain.model.Country

interface CountryRemoteRepository {
    suspend fun getCountries(): List<Country>
}