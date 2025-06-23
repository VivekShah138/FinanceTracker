package com.example.financetracker.setup_account.presentation.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.core.core_presentation.utils.Screens
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

        if(states.isLoading){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                CircularProgressIndicator()
            }
        }else{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .imePadding(),
            ) {
                // Email
                EmailDisplay(label = "Email",
                    email = states.email ?: "Unknown",
                    onChangeEmailClick = {}
                )

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
//                SearchableDropdown3(
//                    label = "Country",
//                    selectedText = states.searchCountry,
//                    expanded = states.countryExpanded,
//                    list = states.countryFilteredSearchList,
//                    onExpandedChange = {
//                        viewModel.onEvent(
//                            ProfileSetUpEvents.SelectCountry(
//                                country = states.selectedCountry,
//                                callingCode = states.callingCode,
//                                expanded = !states.countryExpanded
//                            )
//                        )
//                        if (it) {
//                            viewModel.onEvent(ProfileSetUpEvents.LoadCountries)
//                        }
//                    },
//                    onDismissRequest = {
//                        viewModel.onEvent(
//                            ProfileSetUpEvents.SelectCountry(
//                                country = states.selectedCountry,
//                                callingCode = states.callingCode,
//                                expanded = !states.countryExpanded
//                            )
//                        )
//                    },
//                    displayText = {
//                        if(it.name.common.isEmpty()){
//                            "No Countries To Show"
//                        }else{
//                            it.name.common
//                        }
//                    },
//                    onItemSelect = {
//                        val country = it.name.common
//                        val phoneCode = when {
//                            it.idd?.root == null -> "N/A"
//                            it.idd.suffixes.isNullOrEmpty() -> it.idd.root
//                            it.idd.suffixes.size > 1 -> it.idd.root
//                            else -> it.idd.root + it.idd.suffixes.firstOrNull()
//                        }
//                        val firstCurrency = it.currencies?.entries?.firstOrNull()
//                        val currencyName = firstCurrency?.value?.name ?: "N/A"
//                        val currencySymbol = firstCurrency?.value?.symbol ?: "N/A"
//                        val currencyCode = firstCurrency?.key ?: "N/A"
//
//                        Log.d("ProfileSetUp","firstCurrency Country $firstCurrency")
//                        Log.d("ProfileSetUp","currencyName Country $currencyName")
//                        Log.d("ProfileSetUp","currencyCode Country $currencySymbol")
//                        Log.d("ProfileSetUp","currencySymbol Country $currencyCode")
//
//                        viewModel.onEvent(
//                            ProfileSetUpEvents.SelectBaseCurrency(
//                                currency = currencyName,
//                                currencyCode = currencyCode,
//                                currencySymbol = currencySymbol,
//                                expanded = false
//                            )
//                        )
//                        viewModel.onEvent(
//                            ProfileSetUpEvents.SelectCountry(
//                                country = country,
//                                callingCode = phoneCode,
//                                expanded = !states.countryExpanded
//                            )
//                        )
//                        viewModel.onEvent(
//                            ProfileSetUpEvents.ChangeSearchCountry(country)
//                        )
//                    },
//                    isLoading = states.isLoadingDropdownCountry,
//                    onChangeSearchValue = {
//                        viewModel.onEvent(
//                            ProfileSetUpEvents.ChangeSearchCountry(it)
//                        )
//                        viewModel.onEvent(
//                            ProfileSetUpEvents.FilterCountryNameList(
//                                list = states.countries,
//                                newWord = it
//                            )
//                        )
//                    }
//                )
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
                    category = states.searchCountry,
                    label = "Country",
                    displayText = {
                            it.name.common
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
//                SimpleDropdownMenu3(
//                    label = "Base Currency",
//                    selectedText = "${states.searchCurrency} (${states.baseCurrencyCode})",
//                    expanded = states.baseCurrencyExpanded,
//                    list = states.currencies,
//                    onExpandedChange = {
//                        viewModel.onEvent(
//                            ProfileSetUpEvents.SelectBaseCurrency(
//                                currency = states.selectedBaseCurrency,
//                                currencyCode = states.baseCurrencyCode,
//                                currencySymbol = states.baseCurrencySymbol,
//                                expanded = it
//                            )
//                        )
//                        if (it) {
//                            viewModel.onEvent(ProfileSetUpEvents.LoadCurrencies)
//                        }
//                    },
//                    onDismissRequest = {
//                        viewModel.onEvent(
//                            ProfileSetUpEvents.SelectBaseCurrency(
//                                currency = states.selectedBaseCurrency,
//                                currencyCode = states.baseCurrencyCode,
//                                currencySymbol = states.baseCurrencySymbol,
//                                expanded = !states.baseCurrencyExpanded
//                            )
//                        )
//
//                    },
//                    displayText = {
//                        it.currencies?.entries?.firstOrNull()?.value?.name ?: "N/A"
//                    },
//                    onItemSelect = {
//                        val firstCurrency = it.currencies?.entries?.firstOrNull()
//
//
//
//                        val currencyName = firstCurrency?.value?.name ?: "N/A"
//                        val currencySymbol = firstCurrency?.value?.symbol ?: "N/A"
//                        val currencyCode = firstCurrency?.key ?: "N/A"
//
//                        Log.d("ProfileSetUp","firstCurrency BaseCurrency $firstCurrency")
//                        Log.d("ProfileSetUp","currencyName BaseCurrency $currencyName")
//                        Log.d("ProfileSetUp","currencyCode BaseCurrency $currencySymbol")
//                        Log.d("ProfileSetUp","currencySymbol BaseCurrency $currencyCode")
//
//                        viewModel.onEvent(
//                            ProfileSetUpEvents.SelectBaseCurrency(
//                                currency = currencyName,
//                                currencyCode = currencyCode,
//                                currencySymbol = currencySymbol,
//                                expanded = false
//                            )
//                        )
//                    },
//                    isLoading = states.isLoadingDropdownCurrency
//                )

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
}
