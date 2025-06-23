package com.example.financetracker.setup_account.presentation

import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import com.example.financetracker.setup_account.domain.model.Country

sealed class ProfileSetUpEvents {
    object LoadCountries: ProfileSetUpEvents()
    object LoadCurrencies: ProfileSetUpEvents()
    data class SelectBaseCurrency(val currency: String,val expanded: Boolean,val currencyCode: String,val currencySymbol: String): ProfileSetUpEvents()
    data class SelectCountry(val country: String, val callingCode: String,val expanded: Boolean): ProfileSetUpEvents()
    data class ChangePhoneNumber(val phone:String): ProfileSetUpEvents()
    data class ChangeFirstName(val firstName: String): ProfileSetUpEvents()
    data class ChangeLastName(val lastName: String): ProfileSetUpEvents()
    data class ChangeOnBoardingSteps(val steps: Int): ProfileSetUpEvents()
    data class ChangeSearchCountry(val name: String): ProfileSetUpEvents()
    data class ChangeCountryExpanded(val expanded: Boolean): ProfileSetUpEvents()
    data class ChangeSearchCurrency(val name: String): ProfileSetUpEvents()
    data object ValidateNames: ProfileSetUpEvents()
    data object ValidatePhoneNumber: ProfileSetUpEvents()
    data object ValidateCountry: ProfileSetUpEvents()
    data object Submit: ProfileSetUpEvents()
    data class FilterCurrencyNameList(val list: List<Country>, val newWord: String) : ProfileSetUpEvents()
    data class FilterCountryNameList(val list: List<Country>, val newWord: String) : ProfileSetUpEvents()
}