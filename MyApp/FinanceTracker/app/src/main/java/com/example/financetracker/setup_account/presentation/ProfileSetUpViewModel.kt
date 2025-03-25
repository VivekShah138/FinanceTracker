package com.example.financetracker.setup_account.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.setup_account.domain.usecases.UseCasesWrapperSetupAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileSetUpViewModel @Inject constructor(
    private val useCasesWrapperSetupAccount: UseCasesWrapperSetupAccount
): ViewModel() {

    private val _profileSetUpStates = MutableStateFlow(ProfileSetUpStates())
    val profileSetUpStates : StateFlow<ProfileSetUpStates> = _profileSetUpStates.asStateFlow()

    private val profileSetUpEventChannel = Channel<ProfileUpdateEvent>()
    val profileSetUpValidationEvents = profileSetUpEventChannel.receiveAsFlow()

    init {
        getProfileInfo()
    }

    fun onEvent(profileSetUpEvents: ProfileSetUpEvents){
        when(profileSetUpEvents){
            is ProfileSetUpEvents.ChangeCurrencyExpanded -> {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    baseCurrencyExpanded = profileSetUpEvents.expanded
                )
            }
            is ProfileSetUpEvents.SelectBaseCurrency -> {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    selectedBaseCurrency = profileSetUpEvents.currency
                )
            }
            ProfileSetUpEvents.LoadCountries -> {
                viewModelScope.launch{
                    fetchCountries()
                }
            }
            is ProfileSetUpEvents.SelectCountry -> {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    selectedCountry = profileSetUpEvents.country
                )
            }
            is ProfileSetUpEvents.ChangeCountryExpanded -> {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    countryExpanded = profileSetUpEvents.expanded
                )
            }
            is ProfileSetUpEvents.ChangeFirstName -> {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    firstName = profileSetUpEvents.firstName
                )
            }
            is ProfileSetUpEvents.ChangeLastName -> {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    lastName = profileSetUpEvents.lastName
                )
            }
            is ProfileSetUpEvents.ChangePhoneNumber -> {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    phoneNumber = profileSetUpEvents.phone
                )
            }
            is ProfileSetUpEvents.SelectCallingCode -> {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    callingCode = profileSetUpEvents.callingCode
                )
            }
            ProfileSetUpEvents.LoadCurrencies -> {
                viewModelScope.launch {
                    fetchBaseCurrencies()
                }
            }
            ProfileSetUpEvents.Submit -> {
                viewModelScope.launch {
                    validateFields()
                }

            }
            is ProfileSetUpEvents.ChangeOnBoardingSteps -> {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    onBoardingSteps = profileSetUpEvents.steps
                )
            }
            is ProfileSetUpEvents.ValidateCountry -> {
                viewModelScope.launch {
                    validateCountry()
                }
            }
            ProfileSetUpEvents.ValidateNames-> {
                viewModelScope.launch {
                    validateName()
                }
            }
            ProfileSetUpEvents.ValidatePhoneNumber -> {
                viewModelScope.launch {
                    validatePhoneNumber()
                }
            }
        }
    }

    private suspend fun validateFields(){
        val firstName = useCasesWrapperSetupAccount.validateName(
            _profileSetUpStates.value.firstName
        )
        val lastName = useCasesWrapperSetupAccount.validateName(
            _profileSetUpStates.value.lastName
        )
        val phoneNumber = useCasesWrapperSetupAccount.validatePhoneNumber(
            _profileSetUpStates.value.phoneNumber
        )
        val country = useCasesWrapperSetupAccount.validateCountry(
            _profileSetUpStates.value.selectedCountry
        )

        if(!firstName.isSuccessful || !lastName.isSuccessful || !phoneNumber.isSuccessful || !country.isSuccessful){
            Log.d("ProfileFN", "FN: ${_profileSetUpStates.value.firstName} ${firstName.isSuccessful}  ${firstName.errorMessage}")
            Log.d("ProfileLN", "LN: ${_profileSetUpStates.value.lastName} ${lastName.isSuccessful}  ${lastName.errorMessage}")
            Log.d("ProfilePN", "PN: ${_profileSetUpStates.value.phoneNumber} ${phoneNumber.isSuccessful}  ${phoneNumber.errorMessage}")
            Log.d("ProfileC", "C: ${_profileSetUpStates.value.selectedCountry} ${country.isSuccessful}  ${country.errorMessage}")
            profileSetUpEventChannel.send(ProfileUpdateEvent.Failure(firstName.errorMessage ?: lastName.errorMessage ?: phoneNumber.errorMessage ?: country.errorMessage))
            return
        }
        else{
            try {
                val userId = useCasesWrapperSetupAccount.getUserUIDUseCase()
                useCasesWrapperSetupAccount.updateUserProfile(
                    userId = userId ?: "Unknown",
                    firstName = profileSetUpStates.value.firstName,
                    lastName = profileSetUpStates.value.lastName,
                    email = profileSetUpStates.value.email ?: "Unknown",
                    baseCurrency = profileSetUpStates.value.selectedBaseCurrency,
                    country = profileSetUpStates.value.selectedCountry,
                    callingCode = profileSetUpStates.value.callingCode,
                    phoneNumber = profileSetUpStates.value.phoneNumber,
                )
                profileSetUpEventChannel.send(ProfileUpdateEvent.Success)
            }catch (e:Exception){
                val errorMessage = e.localizedMessage
                profileSetUpEventChannel.send(ProfileUpdateEvent.Failure(errorMessage))
            }

        }
    }

    private suspend fun validateName(){
        val firstName = useCasesWrapperSetupAccount.validateName(
            _profileSetUpStates.value.firstName
        )
        val lastName = useCasesWrapperSetupAccount.validateName(
            _profileSetUpStates.value.lastName
        )
        if(!firstName.isSuccessful || !lastName.isSuccessful){
            Log.d("ProfileFN", "FN: ${_profileSetUpStates.value.firstName} ${firstName.isSuccessful}  ${firstName.errorMessage}")
            Log.d("ProfileLN", "LN: ${_profileSetUpStates.value.lastName} ${lastName.isSuccessful}  ${lastName.errorMessage}")

            profileSetUpEventChannel.send(ProfileUpdateEvent.Failure(firstName.errorMessage ?: lastName.errorMessage))
            return
        }
        profileSetUpEventChannel.send(ProfileUpdateEvent.Success)
    }

    private suspend fun validatePhoneNumber(){
        val phoneNumber = useCasesWrapperSetupAccount.validatePhoneNumber(
            _profileSetUpStates.value.phoneNumber
        )
        if(!phoneNumber.isSuccessful){
            Log.d("ProfilePN", "PN: ${_profileSetUpStates.value.phoneNumber} ${phoneNumber.isSuccessful}  ${phoneNumber.errorMessage}")
            profileSetUpEventChannel.send(ProfileUpdateEvent.Failure(phoneNumber.errorMessage))
            return
        }
        profileSetUpEventChannel.send(ProfileUpdateEvent.Success)
    }

    private suspend fun validateCountry(){
        val country = useCasesWrapperSetupAccount.validateCountry(
            _profileSetUpStates.value.selectedCountry
        )
        if(!country.isSuccessful){
            Log.d("ProfileC", "C: ${_profileSetUpStates.value.selectedCountry} ${country.isSuccessful}  ${country.errorMessage}")
            profileSetUpEventChannel.send(ProfileUpdateEvent.Failure(country.errorMessage))
            return
        }
        profileSetUpEventChannel.send(ProfileUpdateEvent.Success)
    }



    private suspend fun fetchCountries() {
        withContext(Dispatchers.IO) {
            try {
                val sortedCountries = useCasesWrapperSetupAccount.getCountryDetailsUseCase().sortedBy {
                    it.name.common
                }.distinctBy {
                   it.name.common
                }

                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    countries = sortedCountries
                )
            } catch (e: Exception) {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    currencyErrorMessage = e.localizedMessage ?: " Error Occurred"
                )
            }
        }
    }

    private suspend fun fetchBaseCurrencies() {
        withContext(Dispatchers.IO) {
            try {
                val sortedCurrencies = useCasesWrapperSetupAccount.getCountryDetailsUseCase().sortedBy {
                    it.currencies?.entries?.firstOrNull()?.value?.name ?: "N/A"

                }.distinctBy {
                    it.currencies?.entries?.firstOrNull()?.value?.name ?: "N/A"
                }

                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    currencies = sortedCurrencies
                )

            } catch (e: Exception) {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    currencyErrorMessage = e.localizedMessage ?: " Error Occurred"
                )
            }
        }
    }

    private fun getProfileInfo(){
        viewModelScope.launch {
            try {
                val userId = useCasesWrapperSetupAccount.getUserUIDUseCase() ?: "Unknown"
                val userProfile = useCasesWrapperSetupAccount.getUserProfileUseCase(userId)
                if(userProfile == null){
                    profileSetUpEventChannel.send(ProfileUpdateEvent.Failure("Error in Fetching User Details"))
                }
                else{
                    _profileSetUpStates.value = profileSetUpStates.value.copy(
                        firstName = userProfile.firstName,
                        lastName = userProfile.lastName,
                        email = userProfile.email,
                        selectedBaseCurrency = userProfile.baseCurrency,
                        selectedCountry =  userProfile.country,
                        callingCode = userProfile.callingCode,
                        phoneNumber = userProfile.phoneNumber
                    )
                }
            }catch (e:Exception){
                profileSetUpEventChannel.send(ProfileUpdateEvent.Failure("Error in Fetching User Details"))
            }
        }
    }

    sealed class ProfileUpdateEvent{
        data object Success: ProfileUpdateEvent()
        data class Failure(val errorMessage: String?): ProfileUpdateEvent()
    }
}