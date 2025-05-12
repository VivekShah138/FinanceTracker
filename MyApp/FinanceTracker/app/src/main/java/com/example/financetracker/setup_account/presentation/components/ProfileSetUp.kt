package com.example.financetracker.setup_account.presentation.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.core.core_presentation.utils.Screens
import com.example.financetracker.setup_account.domain.model.Country
import com.example.financetracker.setup_account.presentation.ProfileSetUpEvents
import com.example.financetracker.setup_account.presentation.ProfileSetUpViewModel


@Composable
fun ProfileSetUp(
    viewModel: ProfileSetUpViewModel,
    navController: NavController
){
    val states by viewModel.profileSetUpStates.collectAsStateWithLifecycle()
    val profileSetUpValidationEvents = viewModel.profileSetUpValidationEvents
    val context = LocalContext.current



    LaunchedEffect(key1 = context) {
        profileSetUpValidationEvents.collect { event ->
            when (event) {
                is ProfileSetUpViewModel.ProfileUpdateEvent.Failure -> {
                    Toast.makeText(context,event.errorMessage,Toast.LENGTH_SHORT).show()
                }
                is ProfileSetUpViewModel.ProfileUpdateEvent.Success -> {
                    Toast.makeText(context,"Profile Successfully Update",Toast.LENGTH_LONG).show()
                    navController.navigate(Screens.HomePageScreen.routes)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Profile",
                showBackButton = true,
                showMenu = false,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Email
            EmailDisplay(label = "Email",
                email = states.email ?: "Unknown",
                onChangeEmailClick = {}
            )

//            Spacer(modifier = Modifier.height(10.dp))

            // Change Password
//            ChangePasswordSection(onChangePasswordClick = {})

            Spacer(modifier = Modifier.height(10.dp))

            // Names
            Names(firstName = states.firstName,
                lastName = states.lastName,
                onFirstNameChange = {
                    viewModel.onEvent(ProfileSetUpEvents.ChangeFirstName(it))
                },
                onLastNameChange = {
                    viewModel.onEvent(ProfileSetUpEvents.ChangeLastName(it))
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Country/Region
            SimpleDropdownMenu(
                label = "Country",
                selectedText = states.selectedCountry,
                expanded = states.countryExpanded,
                list = states.countries,
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
                onDismissRequest = {
                    viewModel.onEvent(
                        ProfileSetUpEvents.SelectCountry(
                            country = states.selectedCountry,
                            callingCode = states.callingCode,
                            expanded = !states.countryExpanded
                        )
                    )
                },
                displayText = {
                    if(it.name.common.isEmpty()){
                        "No Countries To Show"
                    }else{
                        it.name.common
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
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Phone Number
            PhoneNumberInput(countryCode = states.callingCode,
                phoneNumber = states.phoneNumber,
                onPhoneNumberChange = {
                    viewModel.onEvent(ProfileSetUpEvents.ChangePhoneNumber(it))
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Base Currency
            SimpleDropdownMenu(
                label = "Base Currency",
                selectedText = "${states.selectedBaseCurrency} (${states.baseCurrencyCode})",
                expanded = states.baseCurrencyExpanded,
                list = states.currencies,
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
                onDismissRequest = {
                    viewModel.onEvent(
                        ProfileSetUpEvents.SelectBaseCurrency(
                            currency = states.selectedBaseCurrency,
                            currencyCode = states.baseCurrencyCode,
                            currencySymbol = states.baseCurrencySymbol,
                            expanded = !states.baseCurrencyExpanded
                        )
                    )

                },
                displayText = {
                    it.currencies?.entries?.firstOrNull()?.value?.name ?: "N/A"
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
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Submit
            SaveButton(text = "Save",
                onClick = {
                    viewModel.onEvent(ProfileSetUpEvents.Submit)
                }
            )
        }
    }
}
