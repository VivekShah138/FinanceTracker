package com.example.financetracker.setup_account.domain.usecases

import com.example.financetracker.setup_account.domain.repository.local.CountryLocalRepository

class InsertCountryLocallyWorkManager(
    private val countryLocalRepository: CountryLocalRepository
) {
    suspend operator fun invoke(){
        return countryLocalRepository.insertCountries()
    }
}