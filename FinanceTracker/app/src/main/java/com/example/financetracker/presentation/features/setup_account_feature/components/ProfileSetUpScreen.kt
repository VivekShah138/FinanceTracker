package com.example.financetracker.presentation.features.setup_account_feature.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.presentation.core_components.AppTopBar
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.setup_account_feature.ProfileSetUpEvents
import com.example.financetracker.presentation.features.setup_account_feature.ProfileSetUpStates
import com.example.financetracker.presentation.features.setup_account_feature.ProfileSetUpViewModel
import com.example.financetracker.presentation.features.setup_account_feature.ProfileSetUpViewModel.ProfileUpdateEvent
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


@Composable
fun ProfileSetUpRoot(
    viewModel: ProfileSetUpViewModel = hiltViewModel(),
    navController: NavController
){
    val states by viewModel.profileSetUpStates.collectAsStateWithLifecycle()
    val profileSetUpValidationEvents = viewModel.profileSetUpValidationEvents

    ProfileSetUpScreen(
        navController = navController,
        states = states,
        onEvent = viewModel::onEvent,
        profileSetUpValidationEvents = profileSetUpValidationEvents
    )
}

@Composable
fun ProfileSetUpScreen(
    navController: NavController,
    states: ProfileSetUpStates,
    onEvent: (ProfileSetUpEvents) -> Unit,
    profileSetUpValidationEvents: Flow<ProfileUpdateEvent>
){

    val context = LocalContext.current



    LaunchedEffect(key1 = context) {
        profileSetUpValidationEvents.collect { event ->
            when (event) {
                is ProfileUpdateEvent.Failure -> {
                    Toast.makeText(context,event.errorMessage,Toast.LENGTH_SHORT).show()
                }
                is ProfileUpdateEvent.Success -> {
                    Toast.makeText(context,"Profile Successfully Update",Toast.LENGTH_LONG).show()
                    navController.navigate(Screens.HomePageScreen)
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
                        onEvent(ProfileSetUpEvents.ChangeFirstName(it))
                    },
                    onLastNameChange = {
                        onEvent(ProfileSetUpEvents.ChangeLastName(it))
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))


                AutoComplete(
                    categories = states.countries,
                    loadCountry = {
                        onEvent(
                            ProfileSetUpEvents.LoadCountries
                        )
                    },
                    onSearchValueChange = {
                        onEvent(
                            ProfileSetUpEvents.ChangeSearchCountry(it)
                        )
                        onEvent(
                            ProfileSetUpEvents.FilterCountryNameList(
                                list = states.countries,
                                newWord = it
                            )
                        )
                    },
                    expanded = states.countryExpanded,
                    onExpandedChange = {
                        onEvent(
                            ProfileSetUpEvents.SelectCountry(
                                country = states.selectedCountry,
                                callingCode = states.callingCode,
                                expanded = it
                            )
                        )
                        if (it) {
                            onEvent(ProfileSetUpEvents.LoadCountries)
                        }
                    },
                    onItemSelect = {
                        val country = it.name.common
                        val phoneCode = if (it.idd.root.isEmpty()) {
                            "N/A"
                        } else {
                            val suffix = it.idd.suffixes.firstOrNull().orEmpty()
                            it.idd.root + suffix
                        }
                        val firstCurrency = it.currencies?.entries?.firstOrNull()
                        val currencyName = firstCurrency?.value?.name ?: "N/A"
                        val currencySymbol = firstCurrency?.value?.symbol ?: "N/A"
                        val currencyCode = firstCurrency?.key ?: "N/A"

                        Log.d("ProfileSetUp","firstCurrency Country $firstCurrency")
                        Log.d("ProfileSetUp","currencyName Country $currencyName")
                        Log.d("ProfileSetUp","currencyCode Country $currencySymbol")
                        Log.d("ProfileSetUp","currencySymbol Country $currencyCode")

                        onEvent(
                            ProfileSetUpEvents.SelectBaseCurrency(
                                currency = currencyName,
                                currencyCode = currencyCode,
                                currencySymbol = currencySymbol,
                                expanded = false
                            )
                        )
                        onEvent(
                            ProfileSetUpEvents.SelectCountry(
                                country = country,
                                callingCode = phoneCode,
                                expanded = !states.countryExpanded
                            )
                        )
                        onEvent(
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
                        onEvent(ProfileSetUpEvents.ChangePhoneNumber(it))
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                AutoComplete(
                    categories = states.currencies,
                    loadCountry = {
                        onEvent(
                            ProfileSetUpEvents.LoadCurrencies
                        )
                    },
                    onSearchValueChange = {
                        onEvent(
                            ProfileSetUpEvents.ChangeSearchCurrency(it)
                        )
                        onEvent(
                            ProfileSetUpEvents.FilterCountryNameList(
                                list = states.countries,
                                newWord = it
                            )
                        )
                    },
                    expanded = states.baseCurrencyExpanded,
                    onExpandedChange = {
                        onEvent(
                            ProfileSetUpEvents.SelectBaseCurrency(
                                currency = states.selectedBaseCurrency,
                                currencyCode = states.baseCurrencyCode,
                                currencySymbol = states.baseCurrencySymbol,
                                expanded = it
                            )
                        )
                        if (it) {
                            onEvent(ProfileSetUpEvents.LoadCurrencies)
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

                        onEvent(
                            ProfileSetUpEvents.SelectBaseCurrency(
                                currency = currencyName,
                                currencyCode = currencyCode,
                                currencySymbol = currencySymbol,
                                expanded = false
                            )
                        )
                        onEvent(
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
                        onEvent(ProfileSetUpEvents.Submit)
                    }
                )
            }

        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun ProfileSetUpPreview(){
    FinanceTrackerTheme(darkTheme = true) {

        ProfileSetUpScreen(
            navController = rememberNavController(),
            states = ProfileSetUpStates(
                email = "shahvivek138@gmail.com"
            ),
            onEvent = {

            },
            profileSetUpValidationEvents = emptyFlow()
        )

    }
}
