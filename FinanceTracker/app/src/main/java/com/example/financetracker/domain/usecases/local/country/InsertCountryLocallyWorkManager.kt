package com.example.financetracker.domain.usecases.local.country

import com.example.financetracker.domain.repository.local.CountryLocalRepository

class InsertCountryLocallyWorkManager(
    private val countryLocalRepository: CountryLocalRepository
) {
    suspend operator fun invoke(){
        return countryLocalRepository.insertCountries()
    }
}