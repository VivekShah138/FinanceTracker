package com.example.financetracker.domain.usecases.local.country

import com.example.financetracker.domain.repository.local.CountryLocalRepository

class SeedCountryLocalUseCase(
    private val countryLocalRepository: CountryLocalRepository
) {
    suspend operator fun invoke(){
        return countryLocalRepository.ensureCountriesPopulated()
    }
}