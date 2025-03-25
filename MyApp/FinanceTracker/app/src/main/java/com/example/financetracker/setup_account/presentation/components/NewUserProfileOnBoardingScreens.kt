package com.example.financetracker.setup_account.presentation.components


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
import com.example.financetracker.core.presentation.components.AppTopBar
import com.example.financetracker.core.presentation.utils.Screens

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
                    SimpleDropdownMenu(
                        label = "Country",
                        selectedText = states.selectedCountry,
                        expanded = states.countryExpanded,
                        list = states.countries,
                        onExpandedChange = {
                            viewModel.onEvent(ProfileSetUpEvents.ChangeCountryExpanded(it))
                            if (it) {
                                viewModel.onEvent(ProfileSetUpEvents.LoadCountries)
                            }
                        },
                        onDismissRequest = {
                            viewModel.onEvent(ProfileSetUpEvents.ChangeCountryExpanded(!states.countryExpanded))
                        },
                        displayText = {
                            it.name.common
                        },
                        onItemSelect = {
                            val country = it.name.common
                            val phoneCode =
                                ((it.idd?.root + it.idd?.suffixes?.joinToString("")) ?: "N/A")
                            val firstCurrency = it.currencies?.entries?.firstOrNull()
                            val currencyName = firstCurrency?.value?.name ?: "N/A"
                            val currencySymbol = firstCurrency?.value?.symbol ?: "N/A"

                            viewModel.onEvent(ProfileSetUpEvents.SelectBaseCurrency("$currencyName (${currencySymbol})"))
                            viewModel.onEvent(ProfileSetUpEvents.SelectCountry(country))
                            viewModel.onEvent(ProfileSetUpEvents.SelectCallingCode(phoneCode))
                            viewModel.onEvent(ProfileSetUpEvents.ChangeCountryExpanded(!states.countryExpanded))
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
                    SimpleDropdownMenu(
                        label = "Base Currency",
                        selectedText = states.selectedBaseCurrency,
                        expanded = states.baseCurrencyExpanded,
                        list = states.currencies,
                        onExpandedChange = {
                            viewModel.onEvent(ProfileSetUpEvents.ChangeCurrencyExpanded(it))
                            if (it) {
                                viewModel.onEvent(ProfileSetUpEvents.LoadCurrencies)
                            }
                        },
                        onDismissRequest = {
                            viewModel.onEvent(ProfileSetUpEvents.ChangeCurrencyExpanded(!states.baseCurrencyExpanded))
                        },
                        displayText = {
                            it.currencies?.entries?.firstOrNull()?.value?.name ?: "N/A"
                        },
                        onItemSelect = {
                            val firstCurrency = it.currencies?.entries?.firstOrNull()
                            val currencyName = firstCurrency?.value?.name ?: "N/A"
                            val currencySymbol = firstCurrency?.value?.symbol ?: "N/A"

                            viewModel.onEvent(ProfileSetUpEvents.SelectBaseCurrency("$currencyName (${currencySymbol})"))
                            viewModel.onEvent(ProfileSetUpEvents.ChangeCurrencyExpanded(!states.baseCurrencyExpanded))
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