package com.example.financetracker.main_page_feature.add_transactions.expense.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.core.local.domain.room.usecases.PredefinedCategoriesUseCaseWrapper
import com.example.financetracker.main_page_feature.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.add_transactions.expense.domain.usecases.AddExpenseUseCasesWrapper
import com.example.financetracker.setup_account.domain.model.Currency
import com.example.financetracker.setup_account.domain.usecases.UseCasesWrapperSetupAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val predefinedCategoriesUseCaseWrapper: PredefinedCategoriesUseCaseWrapper,
    private val useCasesWrapperSetupAccount: UseCasesWrapperSetupAccount,
    private val addExpenseUseCasesWrapper: AddExpenseUseCasesWrapper
): ViewModel() {

    private val _addExpenseStates = MutableStateFlow(AddExpenseStates())
    val addExpenseStates : StateFlow<AddExpenseStates> = _addExpenseStates.asStateFlow()

    private val uid = useCasesWrapperSetupAccount.getUIDLocally() ?: "Unknown"

    private val addTransactionEventChannel = Channel<AddTransactionEvent>()
    val addTransactionsValidationEvents = addTransactionEventChannel.receiveAsFlow()


    init {
        setTransactionCurrencyToBase()
    }


    fun onEvent(addExpenseEvents: AddExpenseEvents){
        when(addExpenseEvents){

            is AddExpenseEvents.SelectCategory -> {
                _addExpenseStates.value = addExpenseStates.value.copy(
                    category = addExpenseEvents.categoryName,
                    bottomSheetState = addExpenseEvents.bottomSheetState,
                    alertBoxState = addExpenseEvents.alertBoxState
                )
                Log.d("AddExpense","BottomSheetState Changed")
            }

            is AddExpenseEvents.LoadCategory -> {
                viewModelScope.launch {
                    predefinedCategoriesUseCaseWrapper.getPredefinedCategories(addExpenseEvents.type.lowercase())
                        .collect { categoryList -> // Collect the flow to get the list
                             _addExpenseStates.value = addExpenseStates.value.copy(
                                 categoryList = categoryList
                             )
                        }
                }
            }

            is AddExpenseEvents.ChangeSavedItemState -> {
                _addExpenseStates.value = addExpenseStates.value.copy(
                    saveItemState = addExpenseEvents.state
                )
            }

            is AddExpenseEvents.ChangeRecurringItemState -> {
                _addExpenseStates.value = addExpenseStates.value.copy(
                    isRecurring = addExpenseEvents.state
                )
            }

            is AddExpenseEvents.ChangeTransactionName -> {
                _addExpenseStates.value = addExpenseStates.value.copy(
                    transactionName = addExpenseEvents.name
                )
            }

            AddExpenseEvents.LoadCurrencyRates -> {
                viewModelScope.launch(Dispatchers.IO) {
                    Log.d("AddExpenseViewModel","uid $uid")
                    val baseCurrency = useCasesWrapperSetupAccount.getUserProfileFromLocalDb(uid)?.baseCurrency?.keys?.firstOrNull() ?: "N/A"
                    Log.d("AddExpenseViewModel","baseCurrency $baseCurrency")
                    val exchangeRates = addExpenseUseCasesWrapper.getCurrencyRatesLocally(baseCurrency)
                    Log.d("AddExpenseViewModel","Exchange Rates $exchangeRates")
                }
            }

            is AddExpenseEvents.SaveCustomCategories -> {
                insertCustomCategory()
            }

            is AddExpenseEvents.ChangeTransactionCurrency -> {
                _addExpenseStates.value = addExpenseStates.value.copy(
                    transactionCurrencyName = addExpenseEvents.currencyName,
                    transactionCurrencyCode = addExpenseEvents.currencyCode,
                    transactionCurrencySymbol = addExpenseEvents.currencySymbol,
                    transactionCurrencyExpanded = addExpenseEvents.currencyExpanded
                )
            }

            is AddExpenseEvents.LoadCurrenciesList -> {
                fetchCurrencies()
            }

            is AddExpenseEvents.ChangeTransactionDescription -> {
                _addExpenseStates.value = addExpenseStates.value.copy(
                    transactionDescription = addExpenseEvents.description
                )
            }
            is AddExpenseEvents.ChangeTransactionPrice -> {
                _addExpenseStates.value = addExpenseStates.value.copy(
                    transactionPrice = addExpenseEvents.price
                )
            }

            is AddExpenseEvents.SetConvertedTransactionPrice -> {
                if(_addExpenseStates.value.transactionPrice.isEmpty()){
                    AddTransactionEvent.Failure(
                        errorMessage = "Please Enter the Price"
                    )
                }
                else{
                    fetchCurrenciesExchangeRates()
                }

            }

            is AddExpenseEvents.ShowConversion -> {
                _addExpenseStates.value = addExpenseStates.value.copy(
                    showConversion = addExpenseEvents.showConversion
                )
            }

            AddExpenseEvents.AddExpenseTransaction -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val nameResult = addExpenseUseCasesWrapper.validateTransactionName(_addExpenseStates.value.transactionName)
                    val priceResult = addExpenseUseCasesWrapper.validateTransactionPrice(_addExpenseStates.value.transactionPrice)
                    val categoryResult = addExpenseUseCasesWrapper.validateTransactionCategory(_addExpenseStates.value.category)


                    if(!nameResult.isSuccessful || !priceResult.isSuccessful || !categoryResult.isSuccessful){
                        addTransactionEventChannel.send(
                            AddTransactionEvent.Failure(
                                errorMessage = nameResult.errorMessage ?: priceResult.errorMessage ?: categoryResult.errorMessage
                            )
                        )
                    }
                    else {

                        val transactionCurrencyName = _addExpenseStates.value.transactionCurrencyName
                        val transactionCurrencySymbol = _addExpenseStates.value.transactionCurrencySymbol
                        val transactionCurrencyCode = _addExpenseStates.value.transactionCurrencyCode

                        val selectedTransactionCurrency = Currency(name = transactionCurrencyName, symbol = transactionCurrencySymbol)
                        val transactionCurrency: Map<String, Currency> = mapOf(
                            transactionCurrencyCode to selectedTransactionCurrency
                        )

                        val transaction = Transactions(
                            amount = _addExpenseStates.value.transactionPrice?.toDoubleOrNull() ?: 0.0,
                            currency = transactionCurrency,
                            convertedAmount = _addExpenseStates.value.convertedPrice?.toDoubleOrNull() ?: 0.0,
                            exchangeRate = _addExpenseStates.value.transactionExchangeRate?.toDoubleOrNull() ?: 0.0,
                            transactionType = "expense",
                            category = _addExpenseStates.value.category,
                            dateTime = System.currentTimeMillis(),
                            userUid = uid,
                            description = _addExpenseStates.value.transactionDescription,
                            isRecurring = _addExpenseStates.value.isRecurring,
                            cloudSync = false
                        )
                        try{
                            addExpenseUseCasesWrapper.insertTransactionsLocally(transaction)
                        }catch (e:Exception){
                            addTransactionEventChannel.send(AddTransactionEvent.Failure(errorMessage = e.localizedMessage))
                        }
                        addTransactionEventChannel.send(AddTransactionEvent.Success)
                    }

                }
            }
        }
    }

    private fun fetchCurrenciesExchangeRates(){
        viewModelScope.launch(Dispatchers.IO) {

            val saveCurrencyMap = useCasesWrapperSetupAccount.getUserProfileFromLocalDb(uid)?.baseCurrency
            Log.d("AddExpenseViewModel", "saveCurrencyMap: $saveCurrencyMap")
            val baseCurrencyName = saveCurrencyMap?.values?.firstOrNull()?.name ?: "N/A"
            val baseCurrencyCode = saveCurrencyMap?.keys?.firstOrNull() ?: "N/A"
            val baseCurrencySymbol = saveCurrencyMap?.values?.firstOrNull()?.symbol ?: "N/A"
            Log.d("AddExpenseViewModel", "baseCurrencySymbol: $baseCurrencyCode")
            Log.d("AddExpenseViewModel", "baseCurrencySymbol: $baseCurrencySymbol")

            val currencyExchangeRate = useCasesWrapperSetupAccount.getCurrencyRatesLocally(baseCurrencyCode)?.conversion_rates
            Log.d("AddExpenseViewModel", "currencyExchangeRate: $currencyExchangeRate")
            // Check if the map contains the currency code
            val selectedCurrencyRate = if (currencyExchangeRate != null && currencyExchangeRate.containsKey(_addExpenseStates.value.transactionCurrencyCode)) {
                BigDecimal(currencyExchangeRate[_addExpenseStates.value.transactionCurrencyCode] ?: 1.0)
                    .setScale(4, RoundingMode.HALF_UP)
                    .toString()
            } else {
                "1.0000" // Default value with 4 decimal places
            }
            Log.d("AddExpenseViewModel", "selectedCurrencyRate: $selectedCurrencyRate")

            _addExpenseStates.value = addExpenseStates.value.copy(
                transactionExchangeRate = selectedCurrencyRate
            )

            val priceString = _addExpenseStates.value.transactionPrice
            val rateString = _addExpenseStates.value.transactionExchangeRate
            val price = priceString.toDoubleOrNull() ?: 0.0
            val rate = rateString.toDoubleOrNull() ?: 0.0

            Log.d("AddExpenseViewModel", "price: $price")
            Log.d("AddExpenseViewModel", "rate: $rate")

            if (rate != 0.0) {
                // Perform conversion if both price and rate are valid
                val conversion = try {
                    val result = BigDecimal(price / rate)
                        .setScale(2, RoundingMode.HALF_UP)
                    result.toString()
                } catch (e: ArithmeticException) {
                    // Handle any potential math errors
                    "Error: Invalid conversion"
                }

                _addExpenseStates.value = addExpenseStates.value.copy(
                    convertedPrice = conversion
                )
            } else {
                // Handle the error if rate is zero or invalid
                _addExpenseStates.value = addExpenseStates.value.copy(
                    convertedPrice = "Error: Invalid rate"
                )
            }

        }
    }

    private fun insertCustomCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val uid = useCasesWrapperSetupAccount.getUIDLocally() ?: "Unknown"
            val category = Category(
                uid = uid,
                name = _addExpenseStates.value.category,
                type = "expense",
                icon = "ic_custom",
                isCustom = true
            )
            addExpenseUseCasesWrapper.insertCustomCategory(category)
        }
    }

    private fun setTransactionCurrencyToBase(){
        viewModelScope.launch(Dispatchers.IO) {
            val saveCurrencyMap = useCasesWrapperSetupAccount.getUserProfileFromLocalDb(uid)?.baseCurrency
            val baseCurrencyName = saveCurrencyMap?.values?.firstOrNull()?.name ?: "N/A"
            val baseCurrencyCode = saveCurrencyMap?.keys?.firstOrNull() ?: "N/A"
            val baseCurrencySymbol = saveCurrencyMap?.values?.firstOrNull()?.symbol ?: "N/A"

            _addExpenseStates.value = addExpenseStates.value.copy(
                baseCurrencySymbol = baseCurrencySymbol,
                baseCurrencyName = baseCurrencyName,
                baseCurrencyCode = baseCurrencyCode
            )

            _addExpenseStates.value = addExpenseStates.value.copy(
                transactionCurrencyName = baseCurrencyName,
                transactionCurrencyCode = baseCurrencyCode,
                transactionCurrencySymbol = baseCurrencySymbol,
                transactionCurrencyExpanded = false
            )

        }
    }

    private fun fetchCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val sortedCountries = useCasesWrapperSetupAccount.getCountryDetailsUseCase()
                    .filter {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() != null
                    }
                    .sortedBy {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() ?: ""
                    }
                    .distinctBy {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() ?: ""
                    }

                Log.d("AddExpenseViewModel","currencies $sortedCountries")

                _addExpenseStates.value = addExpenseStates.value.copy(
                    currencies = sortedCountries
                )
            } catch (e: Exception) {
                _addExpenseStates.value = addExpenseStates.value.copy(
                    errorMessage = e.localizedMessage ?: " Error Occurred"
                )

                val sortedCountriesLocal = useCasesWrapperSetupAccount.getCountryLocally()
                    .filter {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() != null
                    }
                    .sortedBy {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() ?: ""
                    }
                    .distinctBy {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() ?: ""
                    }

                _addExpenseStates.value = addExpenseStates.value.copy(
                    currencies = sortedCountriesLocal
                )

                addTransactionEventChannel.send(AddTransactionEvent.Failure("Error in Fetching Currencies From internet.Using Locally Saved Details"))
            }
        }
    }

    sealed class AddTransactionEvent{
        data object Success: AddTransactionEvent()
        data class Failure(val errorMessage: String?): AddTransactionEvent()
    }

}