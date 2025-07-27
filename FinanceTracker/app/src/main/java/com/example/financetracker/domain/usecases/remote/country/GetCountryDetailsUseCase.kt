package com.example.financetracker.domain.usecases.remote.country

import com.example.financetracker.domain.model.Country
import com.example.financetracker.domain.repository.remote.CountryRemoteRepository


class GetCountryDetailsUseCase(
    private val countryRemoteRepository: CountryRemoteRepository
) {

    suspend operator fun invoke(): List<Country>{
        return countryRemoteRepository.getCountries()
    }

}