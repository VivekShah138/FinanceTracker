package com.example.financetracker.setup_account.presentation

sealed class ProfileSetUpEvents {
    object LoadCountries: ProfileSetUpEvents()
    object LoadCurrencies: ProfileSetUpEvents()
    data class SelectBaseCurrency(val currency: String,val expanded: Boolean,val currencyCode: String,val currencySymbol: String): ProfileSetUpEvents()
    data class SelectCountry(val country: String, val callingCode: String,val expanded: Boolean): ProfileSetUpEvents()
    data class ChangePhoneNumber(val phone:String): ProfileSetUpEvents()
    data class ChangeFirstName(val firstName: String): ProfileSetUpEvents()
    data class ChangeLastName(val lastName: String): ProfileSetUpEvents()
    data class ChangeOnBoardingSteps(val steps: Int): ProfileSetUpEvents()
    data object ValidateNames: ProfileSetUpEvents()
    data object ValidatePhoneNumber: ProfileSetUpEvents()
    data object ValidateCountry: ProfileSetUpEvents()
    data object Submit: ProfileSetUpEvents()
}