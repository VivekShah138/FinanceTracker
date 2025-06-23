package com.example.financetracker.setup_account.presentation

import com.example.financetracker.setup_account.domain.model.Country

data class ProfileSetUpStates(
    val email: String? = "",
    val countries: List<Country> = emptyList(),
    val currencies: List<Country> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val callingCode: String = "",
    val baseCurrencyExpanded: Boolean = false,
    val selectedBaseCurrency: String = "Select a Base Currency",
    val baseCurrencyCode: String = "",
    val baseCurrencySymbol: String = "",
    val countryExpanded: Boolean = false,
    val selectedCountry: String = "Select a Country",
    val currencyErrorMessage: String = "",
    val onBoardingSteps: Int = 0,
    val isLoading: Boolean = false,
    val isLoadingDropdownCountry: Boolean = false,
    val isLoadingDropdownCurrency: Boolean = false,
    val searchCountry:String = "",
    val searchCurrency: String = "",
    val currencyFilteredSearchList : List<Country> = emptyList(),
    val countryFilteredSearchList : List<Country> = emptyList(),
)
