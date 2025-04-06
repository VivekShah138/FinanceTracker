package com.example.financetracker.setup_account.data.remote.repository

import android.util.Log
import com.example.financetracker.setup_account.data.remote.CountryApi
import com.example.financetracker.setup_account.domain.model.Country
import com.example.financetracker.setup_account.domain.repository.remote.CountryRemoteRepository

class CountryRemoteRepositoryImpl(
    private val api: CountryApi
): CountryRemoteRepository {
    override suspend fun getCountries(): List<Country> {
        return api.getCountries()
    }
}