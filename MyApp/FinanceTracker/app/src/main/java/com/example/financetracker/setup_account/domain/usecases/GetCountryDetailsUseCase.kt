package com.example.financetracker.setup_account.domain.usecases

import com.example.financetracker.setup_account.domain.model.Country
import com.example.financetracker.setup_account.domain.repository.CountryRepository


class GetCountryDetailsUseCase(
    private val countryRepository: CountryRepository
) {

    suspend operator fun invoke(): List<Country>{
        return countryRepository.getCountries()
    }

}