package com.example.financetracker.setup_account.domain.usecases

import com.example.financetracker.setup_account.domain.model.Country
import com.example.financetracker.setup_account.domain.repository.remote.CountryRemoteRepository


class GetCountryDetailsUseCase(
    private val countryRemoteRepository: CountryRemoteRepository
) {

    suspend operator fun invoke(): List<Country>{
        return countryRemoteRepository.getCountries()
    }

}