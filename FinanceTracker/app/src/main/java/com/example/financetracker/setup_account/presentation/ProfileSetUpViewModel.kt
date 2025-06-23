package com.example.financetracker.setup_account.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsEvents
import com.example.financetracker.setup_account.domain.model.Currency
import com.example.financetracker.setup_account.domain.usecases.SetupAccountUseCasesWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ProfileSetUpViewModel @Inject constructor(
    private val setupAccountUseCasesWrapper: SetupAccountUseCasesWrapper
): ViewModel() {

    private val _profileSetUpStates = MutableStateFlow(ProfileSetUpStates())
    val profileSetUpStates : StateFlow<ProfileSetUpStates> = _profileSetUpStates.asStateFlow()

    private val profileSetUpEventChannel = Channel<ProfileUpdateEvent>()
    val profileSetUpValidationEvents = profileSetUpEventChannel.receiveAsFlow()

    private val userId = setupAccountUseCasesWrapper.getUIDLocally() ?: "Unknown"

    private var oldBaseCurrency = _profileSetUpStates.value.selectedBaseCurrency



    init {
        getProfileInfo()
        setOldBaseCurrency()
    }

    private fun setOldBaseCurrency() {
        viewModelScope.launch(Dispatchers.IO) {
            val userProfile = setupAccountUseCasesWrapper.getUserProfileFromLocalDb(userId)
            val baseCurrency = userProfile?.baseCurrency?.values?.firstOrNull()?.name ?: "N/A"
//            Log.d("WorkManagerCurrencyRates","baseCurrency $baseCurrency")

            oldBaseCurrency = baseCurrency
//            Log.d("WorkManagerCurrencyRates","oldBaseCurrency $oldBaseCurrency")
        }
    }

    fun onEvent(profileSetUpEvents: ProfileSetUpEvents){
        when(profileSetUpEvents){

            is ProfileSetUpEvents.SelectBaseCurrency -> {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    selectedBaseCurrency = profileSetUpEvents.currency,
                    baseCurrencyExpanded = profileSetUpEvents.expanded,
                    baseCurrencyCode = profileSetUpEvents.currencyCode,
                    baseCurrencySymbol = profileSetUpEvents.currencySymbol
                )
            }
            ProfileSetUpEvents.LoadCountries -> {
                viewModelScope.launch{
                    fetchCountries()
                }
            }
            is ProfileSetUpEvents.SelectCountry -> {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    selectedCountry = profileSetUpEvents.country,
                    callingCode = profileSetUpEvents.callingCode,
                    countryExpanded = profileSetUpEvents.expanded,
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
            is ProfileSetUpEvents.ChangeSearchCountry -> {
                _profileSetUpStates.value  = _profileSetUpStates.value.copy(
                    searchCountry = profileSetUpEvents.name
                )
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    countryFilteredSearchList = emptyList()
                )
            }
            is ProfileSetUpEvents.ChangeSearchCurrency -> {
                _profileSetUpStates.value  = _profileSetUpStates.value.copy(
                    searchCurrency = profileSetUpEvents.name
                )
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    currencyFilteredSearchList = emptyList()
                )
            }
            is ProfileSetUpEvents.FilterCountryNameList -> {

                Log.d("ProfileCountry", "new Word:  ${profileSetUpEvents.newWord}")

                if (profileSetUpEvents.newWord.isEmpty()) {
                    viewModelScope.launch {
                        fetchCountries()
                    }
                } else {
                    val filterList = profileSetUpEvents.list.filter {
                        it.name.common.contains(
                            profileSetUpEvents.newWord,
                            ignoreCase = true
                        )
                    }
                    Log.d("ProfileCountry", "filter :  ${filterList}")
                    _profileSetUpStates.value = profileSetUpStates.value.copy(
                        countryFilteredSearchList = filterList
                    )
                }
            }
            is  ProfileSetUpEvents.FilterCurrencyNameList -> {

                if (profileSetUpEvents.newWord.isEmpty()) {
                    viewModelScope.launch {
                        fetchBaseCurrencies()
                    }
                } else {
                    val filterList = profileSetUpEvents.list.filter {
                        val currencyName = it.currencies.entries.first().value.name
                            currencyName.contains(
                                profileSetUpEvents.newWord,
                                ignoreCase = true
                        )
                    }
                    _profileSetUpStates.value = profileSetUpStates.value.copy(
                        currencyFilteredSearchList = filterList
                    )
                }
            }
            is ProfileSetUpEvents.ChangeCountryExpanded -> {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    baseCurrencyExpanded = profileSetUpEvents.expanded
                )
            }
        }
    }

    private suspend fun validateFields(){
        val firstName = setupAccountUseCasesWrapper.validateName(
            _profileSetUpStates.value.firstName
        )
        val lastName = setupAccountUseCasesWrapper.validateName(
            _profileSetUpStates.value.lastName
        )
        val phoneNumber = setupAccountUseCasesWrapper.validatePhoneNumber(
            _profileSetUpStates.value.phoneNumber
        )
        val country = setupAccountUseCasesWrapper.validateCountry(
            _profileSetUpStates.value.selectedCountry
        )


        if(!firstName.isSuccessful || !lastName.isSuccessful || !phoneNumber.isSuccessful || !country.isSuccessful){
//            Log.d("ProfileFN", "FN: ${_profileSetUpStates.value.firstName} ${firstName.isSuccessful}  ${firstName.errorMessage}")
//            Log.d("ProfileLN", "LN: ${_profileSetUpStates.value.lastName} ${lastName.isSuccessful}  ${lastName.errorMessage}")
//            Log.d("ProfilePN", "PN: ${_profileSetUpStates.value.phoneNumber} ${phoneNumber.isSuccessful}  ${phoneNumber.errorMessage}")
//            Log.d("ProfileC", "C: ${_profileSetUpStates.value.selectedCountry} ${country.isSuccessful}  ${country.errorMessage}")
            profileSetUpEventChannel.send(ProfileUpdateEvent.Failure(firstName.errorMessage ?: lastName.errorMessage ?: phoneNumber.errorMessage ?: country.errorMessage))
            return
        }
        else{
            _profileSetUpStates.value = profileSetUpStates.value.copy(isLoading = true)
            try {
                val userId = setupAccountUseCasesWrapper.getUserUIDUseCase() ?: userId

                val baseCurrencyCode = profileSetUpStates.value.baseCurrencyCode
                val baseCurrencyName = profileSetUpStates.value.selectedBaseCurrency
                val baseCurrencySymbol = profileSetUpStates.value.baseCurrencySymbol

                Log.d("ProfileSetUpViewModel","BaseCurrencyCode firebaseUpdate $baseCurrencyCode")
                Log.d("ProfileSetUpViewModel","BaseCurrencyName firebaseUpdate $baseCurrencyName")
                Log.d("ProfileSetUpViewModel","BaseCurrencySymbol firebaseUpdate $baseCurrencySymbol")

                // Create the Currency object
                val selectedCurrency = Currency(name = baseCurrencyName, symbol = baseCurrencySymbol)

//                Log.d("ProfileSetUpViewModel","selectedCurrency firebaseUpdate $selectedCurrency")

                // Create the baseCurrency map with the code as key and the map as value
                val baseCurrency: Map<String, Currency> = mapOf(
                    baseCurrencyCode to selectedCurrency  // Map the code to the map of currency details
                )

                Log.d("ProfileSetUpViewModel","baseCurrency firebaseUpdate $baseCurrency")

                setupAccountUseCasesWrapper.updateUserProfile(
                    userId = userId ?: "Unknown",
                    firstName = profileSetUpStates.value.firstName,
                    lastName = profileSetUpStates.value.lastName,
                    email = profileSetUpStates.value.email ?: "Unknown",
                    baseCurrency = baseCurrency,
                    country = profileSetUpStates.value.selectedCountry,
                    callingCode = profileSetUpStates.value.callingCode,
                    phoneNumber = profileSetUpStates.value.phoneNumber,
                )

                // Save To LocalDb
                setupAccountUseCasesWrapper.insertUserProfileToLocalDb(
                    userProfile = UserProfile(
                        firstName = profileSetUpStates.value.firstName,
                        lastName = profileSetUpStates.value.lastName,
                        email = profileSetUpStates.value.email ?: "Unknown",
                        baseCurrency = baseCurrency,
                        country = profileSetUpStates.value.selectedCountry,
                        callingCode = profileSetUpStates.value.callingCode,
                        phoneNumber = profileSetUpStates.value.phoneNumber,
                        profileSetUpCompleted = true
                    ),
                    uid = userId
                )

                updateCurrencyRates()

                setupAccountUseCasesWrapper.keepUserLoggedIn(keepLoggedIn = true)

                profileSetUpEventChannel.send(ProfileUpdateEvent.Success)
            }catch (e:Exception){
                val errorMessage = e.localizedMessage
                profileSetUpEventChannel.send(ProfileUpdateEvent.Failure(errorMessage))
            }finally {
                _profileSetUpStates.value = profileSetUpStates.value.copy(isLoading = false)
            }

        }
    }

    private suspend fun validateName(){
        val firstName = setupAccountUseCasesWrapper.validateName(
            _profileSetUpStates.value.firstName
        )
        val lastName = setupAccountUseCasesWrapper.validateName(
            _profileSetUpStates.value.lastName
        )
        if(!firstName.isSuccessful || !lastName.isSuccessful){
//            Log.d("ProfileFN", "FN: ${_profileSetUpStates.value.firstName} ${firstName.isSuccessful}  ${firstName.errorMessage}")
//            Log.d("ProfileLN", "LN: ${_profileSetUpStates.value.lastName} ${lastName.isSuccessful}  ${lastName.errorMessage}")

            profileSetUpEventChannel.send(ProfileUpdateEvent.Failure(firstName.errorMessage ?: lastName.errorMessage))
            return
        }
        profileSetUpEventChannel.send(ProfileUpdateEvent.Success)
    }

    private suspend fun validatePhoneNumber(){
        val phoneNumber = setupAccountUseCasesWrapper.validatePhoneNumber(
            _profileSetUpStates.value.phoneNumber
        )
        if(!phoneNumber.isSuccessful){
//            Log.d("ProfilePN", "PN: ${_profileSetUpStates.value.phoneNumber} ${phoneNumber.isSuccessful}  ${phoneNumber.errorMessage}")
            profileSetUpEventChannel.send(ProfileUpdateEvent.Failure(phoneNumber.errorMessage))
            return
        }
        profileSetUpEventChannel.send(ProfileUpdateEvent.Success)
    }

    private suspend fun validateCountry(){
        val country = setupAccountUseCasesWrapper.validateCountry(
            _profileSetUpStates.value.selectedCountry
        )
        val countryExpanded = _profileSetUpStates.value.countryExpanded

        if(!country.isSuccessful ){
//            Log.d("ProfileC", "C: ${_profileSetUpStates.value.selectedCountry} ${country.isSuccessful}  ${country.errorMessage}")
            profileSetUpEventChannel.send(ProfileUpdateEvent.Failure(country.errorMessage))
            return
        }
        if(countryExpanded){
            profileSetUpEventChannel.send(ProfileUpdateEvent.Failure("Please Select The Country"))
            return
        }
        profileSetUpEventChannel.send(ProfileUpdateEvent.Success)
    }



    private suspend fun fetchCountries() {
        withContext(Dispatchers.IO) {
            _profileSetUpStates.value = profileSetUpStates.value.copy(isLoadingDropdownCountry = true)
            try {
                val sortedCountries = setupAccountUseCasesWrapper.getCountryDetailsUseCase()
                    .filter {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() != null
                    }
                    .sortedBy {
                        it.name.common
                    }
                    .distinctBy {
                        it.name.common
                    }

                setupAccountUseCasesWrapper.insertCountryLocally(sortedCountries)

                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    countries = sortedCountries,
//                    countryFilteredSearchList = sortedCountries
                )
            } catch (e: Exception) {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    currencyErrorMessage = e.localizedMessage ?: " Error Occurred"
                )

                val sortedCountriesLocally = setupAccountUseCasesWrapper.getCountryLocally()
                    .filter {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() != null
                    }
                    .sortedBy {
                        it.name.common
                    }
                    .distinctBy {
                        it.name.common
                    }

                if(sortedCountriesLocally.isEmpty()){
                    profileSetUpEventChannel.send(ProfileUpdateEvent.Failure("Error in Fetching Country From internet.Try Again Later"))
                    setupAccountUseCasesWrapper.insertCountryLocallyWorkManager()
                }
                else{
                    _profileSetUpStates.value = profileSetUpStates.value.copy(
                        countries = sortedCountriesLocally
                    )

                    profileSetUpEventChannel.send(ProfileUpdateEvent.Failure("Error in Fetching Country From internet.Using Locally Saved Details"))
                }
            }finally {
                _profileSetUpStates.value = profileSetUpStates.value.copy(isLoadingDropdownCountry = false)
            }
        }
    }

    private suspend fun fetchBaseCurrencies() {
        withContext(Dispatchers.IO) {
            _profileSetUpStates.value = profileSetUpStates.value.copy(isLoadingDropdownCurrency = true)
            try {
                val sortedCurrencies = setupAccountUseCasesWrapper.getCountryDetailsUseCase()
                    .filter {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() != null
                    }
                    .sortedBy {
                        it.currencies?.entries?.firstOrNull()?.value?.name ?: "N/A"
                    }
                    .distinctBy {
                        it.currencies?.entries?.firstOrNull()?.value?.name ?: "N/A"
                    }

                setupAccountUseCasesWrapper.insertCountryLocally(sortedCurrencies)

                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    currencies = sortedCurrencies,
                    currencyFilteredSearchList = sortedCurrencies
                )

            } catch (e: Exception) {
                _profileSetUpStates.value = profileSetUpStates.value.copy(
                    currencyErrorMessage = e.localizedMessage ?: " Error Occurred"
                )

                val sortedCurrenciesLocally = setupAccountUseCasesWrapper.getCountryLocally()
                    .filter {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() != null
                    }
                    .sortedBy {
                        it.currencies?.entries?.firstOrNull()?.value?.name ?: "N/A"
                    }
                    .distinctBy {
                        it.currencies?.entries?.firstOrNull()?.value?.name ?: "N/A"
                    }

                if(sortedCurrenciesLocally.isEmpty()){
                    profileSetUpEventChannel.send(ProfileUpdateEvent.Failure("Error in Fetching Currencies From internet.Try Again Later"))
                    setupAccountUseCasesWrapper.insertCountryLocallyWorkManager()
                }
                else{
                    _profileSetUpStates.value = profileSetUpStates.value.copy(
                        currencies = sortedCurrenciesLocally
                    )

                    profileSetUpEventChannel.send(ProfileUpdateEvent.Failure("Error in Fetching Currencies From internet.Using Locally Saved Details"))
                }

            }finally {
                _profileSetUpStates.value = profileSetUpStates.value.copy(isLoadingDropdownCurrency = false)
            }
        }
    }

    private fun getProfileInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            _profileSetUpStates.value = profileSetUpStates.value.copy(isLoading = true)
            try {
                val userId2 = setupAccountUseCasesWrapper.getUserUIDUseCase() ?: "Unknown"
                val userProfile = setupAccountUseCasesWrapper.getUserProfileUseCase(userId2)
                if(userProfile == null){
                    profileSetUpEventChannel.send(ProfileUpdateEvent.Failure("Error in Fetching User Details"))
                }
                else {
                    val baseCurrencyCode = userProfile.baseCurrency.keys.firstOrNull() ?: "N/A"
                    val baseCurrencyName = userProfile.baseCurrency?.values?.firstOrNull()?.name ?: "N/A"
                    val baseCurrencySymbol = userProfile.baseCurrency?.values?.firstOrNull()?.symbol ?: "N/A"

                    Log.d("ProfileSetUpViewModel","baseCurrencyCode Firebase Receive $baseCurrencyCode")
                    Log.d("ProfileSetUpViewModel","baseCurrencyName Firebase Receive $baseCurrencyName")
                    Log.d("ProfileSetUpViewModel","baseCurrencySymbol Firebase Receive $baseCurrencySymbol")

                    _profileSetUpStates.value = profileSetUpStates.value.copy(
                        firstName = userProfile.firstName,
                        lastName = userProfile.lastName,
                        email = userProfile.email,
                        selectedBaseCurrency = baseCurrencyName,
                        searchCurrency = baseCurrencyName,
                        baseCurrencyCode = baseCurrencyCode,
                        baseCurrencySymbol = baseCurrencySymbol,
                        selectedCountry =  userProfile.country,
                        searchCountry = userProfile.country,
                        callingCode = userProfile.callingCode,
                        phoneNumber = userProfile.phoneNumber
                    )

                    Log.d("ProfileSetUpViewModel","baseCurrencyCode state ${_profileSetUpStates.value.baseCurrencyCode}")
                    Log.d("ProfileSetUpViewModel","baseCurrencyName state ${_profileSetUpStates.value.selectedBaseCurrency}")
                    Log.d("ProfileSetUpViewModel","baseCurrencySymbol state ${_profileSetUpStates.value.baseCurrencySymbol}")

                }
            }catch (e:Exception){
                profileSetUpEventChannel.send(ProfileUpdateEvent.Failure("Error in Fetching User Details From Cloud.Using Locally Saved Details"))
                val userId = setupAccountUseCasesWrapper.getUIDLocally() ?: "Unknown"
                val userProfile = setupAccountUseCasesWrapper.getUserProfileFromLocalDb(userId)
                if(userProfile == null){
                    profileSetUpEventChannel.send(ProfileUpdateEvent.Failure("Error in Fetching User Details"))
                }
                else{
                    val baseCurrencyCode = userProfile.baseCurrency.keys.firstOrNull() ?: "N/A"
                    val baseCurrencyName = userProfile.baseCurrency?.values?.firstOrNull()?.name ?: "N/A"
                    val baseCurrencySymbol = userProfile.baseCurrency?.values?.firstOrNull()?.symbol ?: "N/A"


                    _profileSetUpStates.value = profileSetUpStates.value.copy(
                        firstName = userProfile.firstName,
                        lastName = userProfile.lastName,
                        email = userProfile.email,
                        selectedBaseCurrency = baseCurrencyName,
                        baseCurrencyCode = baseCurrencyCode,
                        baseCurrencySymbol = baseCurrencySymbol,
                        selectedCountry =  userProfile.country,
                        callingCode = userProfile.callingCode,
                        phoneNumber = userProfile.phoneNumber,
                        searchCountry = userProfile.country,
                        searchCurrency = baseCurrencyName,
                    )
                }
            }finally {
                _profileSetUpStates.value = profileSetUpStates.value.copy(isLoading = false)
            }
        }
    }


    private fun updateCurrencyRates(){
        viewModelScope.launch(Dispatchers.IO) {
            val userProfile = setupAccountUseCasesWrapper.getUserProfileFromLocalDb(userId)

            if(((!oldBaseCurrency.isNullOrEmpty() && oldBaseCurrency != _profileSetUpStates.value.selectedBaseCurrency) || userProfile == null)){
                setupAccountUseCasesWrapper.setCurrencyRatesUpdated(isUpdated = false)
                setupAccountUseCasesWrapper.insertCurrencyRatesLocalOneTime()
            }

        }
    }

    sealed class ProfileUpdateEvent{
        data object Success: ProfileUpdateEvent()
        data class Failure(val errorMessage: String?): ProfileUpdateEvent()
    }
}