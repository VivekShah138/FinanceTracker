package com.example.financetracker.setup_account.presentation.components


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.financetracker.setup_account.presentation.ProfileSetUpEvents
import com.example.financetracker.setup_account.presentation.ProfileSetUpViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.core.core_presentation.utils.Screens

@Composable
fun NewUserProfileOnBoardingScreens(
    viewModel: ProfileSetUpViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val states by viewModel.profileSetUpStates.collectAsStateWithLifecycle()
    val profileSetUpValidationEvents = viewModel.profileSetUpValidationEvents

    val onboardingFields = listOf(
        "Name",
        "Country",
        "Phone Number",
        "Currency"
    )

    fun previousStep() {
        if (states.onBoardingSteps > 0) {
            viewModel.onEvent(ProfileSetUpEvents.ChangeOnBoardingSteps(states.onBoardingSteps - 1))
        } else {
            navController.popBackStack()
        }
    }

    fun nextStep() {
        if (states.onBoardingSteps < onboardingFields.size - 1) {
            viewModel.onEvent(ProfileSetUpEvents.ChangeOnBoardingSteps(states.onBoardingSteps + 1))
        } else {
            viewModel.onEvent(ProfileSetUpEvents.Submit)
        }
    }

    LaunchedEffect(states.onBoardingSteps) {
        profileSetUpValidationEvents.collect { event ->
            when (event) {
                is ProfileSetUpViewModel.ProfileUpdateEvent.Failure -> {
                    Toast.makeText(context, event.errorMessage, Toast.LENGTH_SHORT).show()
                }

                is ProfileSetUpViewModel.ProfileUpdateEvent.Success -> {
                    if(states.onBoardingSteps < onboardingFields.size -1){
                        nextStep()
                    }
                    else{
                        Toast.makeText(context,"Profile Successfully Update",Toast.LENGTH_SHORT).show()
                        navController.navigate(Screens.HomePageScreen.routes)
                    }
                }
            }
        }
    }





    // Display current field
    val currentField = onboardingFields[states.onBoardingSteps]

    // Display UI
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Complete Profile Setup",
                showBackButton = true,
                showMenu = false,
                onBackClick = {
                    previousStep()
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Please enter your $currentField")

            Spacer(modifier = Modifier.height(16.dp))

            // Dynamically show input fields
            when (currentField) {
                "Name" -> {
                    Names(
                        firstName = states.firstName,
                        lastName = states.lastName,
                        onLastNameChange = {
                            viewModel.onEvent(ProfileSetUpEvents.ChangeLastName(it))
                        },
                        onFirstNameChange = {
                            viewModel.onEvent(ProfileSetUpEvents.ChangeFirstName(it))
                        }
                    )
                }
                "Country" -> {
                    AutoComplete(
                        categories = states.countries,
                        loadCountry = {
                            viewModel.onEvent(
                                ProfileSetUpEvents.LoadCountries
                            )
                        },
                        onSearchValueChange = {
                            viewModel.onEvent(
                                ProfileSetUpEvents.ChangeSearchCountry(it)
                            )
                            viewModel.onEvent(
                                ProfileSetUpEvents.FilterCountryNameList(
                                    list = states.countries,
                                    newWord = it
                                )
                            )
                        },
                        expanded = states.countryExpanded,
                        onExpandedChange = {
                            viewModel.onEvent(
                                ProfileSetUpEvents.SelectCountry(
                                    country = states.selectedCountry,
                                    callingCode = states.callingCode,
                                    expanded = it
                                )
                            )
                            if (it) {
                                viewModel.onEvent(ProfileSetUpEvents.LoadCountries)
                            }
                        },
                        onItemSelect = {
                            val country = it.name.common
                            val phoneCode = when {
                                it.idd?.root == null -> "N/A"
                                it.idd.suffixes.isNullOrEmpty() -> it.idd.root
                                it.idd.suffixes.size > 1 -> it.idd.root
                                else -> it.idd.root + it.idd.suffixes.firstOrNull()
                            }
                            val firstCurrency = it.currencies?.entries?.firstOrNull()
                            val currencyName = firstCurrency?.value?.name ?: "N/A"
                            val currencySymbol = firstCurrency?.value?.symbol ?: "N/A"
                            val currencyCode = firstCurrency?.key ?: "N/A"

                            Log.d("ProfileSetUp","firstCurrency Country $firstCurrency")
                            Log.d("ProfileSetUp","currencyName Country $currencyName")
                            Log.d("ProfileSetUp","currencyCode Country $currencySymbol")
                            Log.d("ProfileSetUp","currencySymbol Country $currencyCode")

                            viewModel.onEvent(
                                ProfileSetUpEvents.SelectBaseCurrency(
                                    currency = currencyName,
                                    currencyCode = currencyCode,
                                    currencySymbol = currencySymbol,
                                    expanded = false
                                )
                            )
                            viewModel.onEvent(
                                ProfileSetUpEvents.SelectCountry(
                                    country = country,
                                    callingCode = phoneCode,
                                    expanded = !states.countryExpanded
                                )
                            )
                            viewModel.onEvent(
                                ProfileSetUpEvents.ChangeSearchCountry(country)
                            )
                        },
                        category = if(states.countryExpanded) states.searchCountry else states.selectedCountry,
                        label = "Country",
                        displayText = {
                            it.name.common
                        }
                    )
                }

                "Phone Number" -> {
                    PhoneNumberInput(
                        countryCode = states.callingCode,
                        phoneNumber = states.phoneNumber,
                        onPhoneNumberChange = {
                            viewModel.onEvent(ProfileSetUpEvents.ChangePhoneNumber(it))
                        }
                    )
                }


                "Currency" -> {
                    AutoComplete(
                        categories = states.currencies,
                        loadCountry = {
                            viewModel.onEvent(
                                ProfileSetUpEvents.LoadCurrencies
                            )
                        },
                        onSearchValueChange = {
                            viewModel.onEvent(
                                ProfileSetUpEvents.ChangeSearchCurrency(it)
                            )
                            viewModel.onEvent(
                                ProfileSetUpEvents.FilterCountryNameList(
                                    list = states.countries,
                                    newWord = it
                                )
                            )
                        },
                        expanded = states.baseCurrencyExpanded,
                        onExpandedChange = {
                            viewModel.onEvent(
                                ProfileSetUpEvents.SelectBaseCurrency(
                                    currency = states.selectedBaseCurrency,
                                    currencyCode = states.baseCurrencyCode,
                                    currencySymbol = states.baseCurrencySymbol,
                                    expanded = it
                                )
                            )
                            if (it) {
                                viewModel.onEvent(ProfileSetUpEvents.LoadCurrencies)
                            }
                        },
                        onItemSelect = {
                            val firstCurrency = it.currencies?.entries?.firstOrNull()



                            val currencyName = firstCurrency?.value?.name ?: "N/A"
                            val currencySymbol = firstCurrency?.value?.symbol ?: "N/A"
                            val currencyCode = firstCurrency?.key ?: "N/A"

                            Log.d("ProfileSetUp","firstCurrency BaseCurrency $firstCurrency")
                            Log.d("ProfileSetUp","currencyName BaseCurrency $currencyName")
                            Log.d("ProfileSetUp","currencyCode BaseCurrency $currencySymbol")
                            Log.d("ProfileSetUp","currencySymbol BaseCurrency $currencyCode")

                            viewModel.onEvent(
                                ProfileSetUpEvents.SelectBaseCurrency(
                                    currency = currencyName,
                                    currencyCode = currencyCode,
                                    currencySymbol = currencySymbol,
                                    expanded = false
                                )
                            )
                            viewModel.onEvent(
                                ProfileSetUpEvents.ChangeSearchCurrency(currencyName)
                            )
                        },
                        category = if(states.baseCurrencyExpanded) states.searchCurrency else "${states.selectedBaseCurrency} (${states.baseCurrencyCode})",
                        label = "Base Currency",
                        displayText = {
                            it.currencies?.entries?.firstOrNull()?.value?.name ?: "N/A"
                        }
                    )

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            SaveButton(
                text = if (states.onBoardingSteps == onboardingFields.size - 1) "Submit" else "Next",
                onClick = {
                    when(currentField){
                        "Name" -> viewModel.onEvent(ProfileSetUpEvents.ValidateNames)
                        "Country" -> viewModel.onEvent(ProfileSetUpEvents.ValidateCountry)
                        "Phone Number" -> viewModel.onEvent(ProfileSetUpEvents.ValidatePhoneNumber)
                        "Currency" -> nextStep()
                    }
                }
            )
        }
    }
}