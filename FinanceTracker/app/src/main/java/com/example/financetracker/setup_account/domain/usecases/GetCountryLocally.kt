package com.example.financetracker.setup_account.domain.usecases

import com.example.financetracker.setup_account.domain.model.Country
import com.example.financetracker.setup_account.domain.repository.local.CountryLocalRepository

class GetCountryLocally(
    private val countryLocalRepository: CountryLocalRepository
) {

    suspend operator fun invoke(): List<Country>{
        return countryLocalRepository.getCountries()
    }

}