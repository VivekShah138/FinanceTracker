package com.example.financetracker.domain.usecases.local.country

import com.example.financetracker.domain.model.Country
import com.example.financetracker.domain.repository.local.CountryLocalRepository

class GetCountryLocally(
    private val countryLocalRepository: CountryLocalRepository
) {

    suspend operator fun invoke(): List<Country>{
        return countryLocalRepository.getCountries()
    }

}