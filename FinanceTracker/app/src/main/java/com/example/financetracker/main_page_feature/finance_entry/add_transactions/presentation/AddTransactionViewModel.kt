package com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.core.local.domain.room.usecases.PredefinedCategoriesUseCaseWrapper
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.AddTransactionUseCasesWrapper
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.SavedItemsUseCasesWrapper
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
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val predefinedCategoriesUseCaseWrapper: PredefinedCategoriesUseCaseWrapper,
    private val setupAccountUseCasesWrapper: SetupAccountUseCasesWrapper,
    private val addTransactionUseCasesWrapper: AddTransactionUseCasesWrapper,
    private val savedItemUseCasesWrapper: SavedItemsUseCasesWrapper
): ViewModel() {

    private val _addTransactionStates = MutableStateFlow(AddTransactionStates())
    val addTransactionStates : StateFlow<AddTransactionStates> = _addTransactionStates.asStateFlow()

    private val _selectedItem = MutableStateFlow<SavedItems?>(null)
    val selectedItem: StateFlow<SavedItems?> = _selectedItem.asStateFlow()



    private val uid = setupAccountUseCasesWrapper.getUIDLocally() ?: "Unknown"
    private val cloudSyncStatus = addTransactionUseCasesWrapper.getCloudSyncLocally()


    private val addTransactionValidationEventChannel = Channel<AddTransactionValidationEvent>()
    val addTransactionsValidationEvents = addTransactionValidationEventChannel.receiveAsFlow()


    init {
        setTransactionCurrencyToBase()
    }


    fun onEvent(addTransactionEvents: AddTransactionEvents){
        when(addTransactionEvents){

            // Category
            is AddTransactionEvents.SelectCategory -> {
                _addTransactionStates.value = addTransactionStates.value.copy(
                    category = addTransactionEvents.categoryName,
                    bottomSheetState = addTransactionEvents.bottomSheetState,
                    alertBoxState = addTransactionEvents.alertBoxState
                )
//                Log.d("AddExpense","BottomSheetState Changed")
            }
            is AddTransactionEvents.LoadCategory -> {

                viewModelScope.launch {

                    if(_addTransactionStates.value.transactionType.isEmpty()){
                        addTransactionValidationEventChannel.send(AddTransactionValidationEvent.Failure("please select transaction type"))
                    }
                    else{
                        predefinedCategoriesUseCaseWrapper.getAllCategories(type = addTransactionEvents.type.lowercase(),uid = uid)
                            .collect { categoryList ->
                                _addTransactionStates.value = addTransactionStates.value.copy(
                                    categoryList = categoryList
                                )
                            }
                    }
                }
            }
            is AddTransactionEvents.SaveCustomCategories -> {
                insertCustomCategory()
            }

            // Saved Item Transaction
            is AddTransactionEvents.ChangeSavedItemState -> {
                _addTransactionStates.value = addTransactionStates.value.copy(
                    saveItemState = addTransactionEvents.state
                )
            }
            is AddTransactionEvents.LoadSavedItemList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    loadSavedItemList()
                }
            }
            is AddTransactionEvents.FilterSavedItemList -> {
                val filterList = addTransactionEvents.list.filter {
                    it.itemName.contains(
                        addTransactionEvents.newWord,
                        ignoreCase = true
                    )
                }
                _addTransactionStates.value = addTransactionStates.value.copy(
                    transactionSearchFilteredList = filterList
                )
            }
            is AddTransactionEvents.ChangeSavedItemSearchState -> {

//                Log.d("AddTransactionPage","searchBarFocus viewModel Before ${_addTransactionStates.value.searchBarFocusedState}")

                _addTransactionStates.value = addTransactionStates.value.copy(
                    searchBarFocusedState = addTransactionEvents.state
                )

//                Log.d("AddTransactionPage","searchBarFocus viewModel After ${_addTransactionStates.value.searchBarFocusedState}")
            }
            is AddTransactionEvents.ChangeQuantity -> {
                _addTransactionStates.value = addTransactionStates.value.copy(
                    quantityBottomSheetState = addTransactionEvents.state
                )
            }
            is AddTransactionEvents.CalculateFinalPrice -> {
                val quantity = addTransactionEvents.quantity
                val price = addTransactionEvents.price
                val finalPrice = BigDecimal(price * quantity)
                    .setScale(2, RoundingMode.HALF_UP)
                val finalPriceString = finalPrice.toString()

                _addTransactionStates.value = addTransactionStates.value.copy(
                    transactionPrice = finalPriceString
                )
            }
            is AddTransactionEvents.ChangeSelectedItem -> {
                _selectedItem.value = addTransactionEvents.item
            }

            // Recurring Transaction
            is AddTransactionEvents.ChangeRecurringItemState -> {
                _addTransactionStates.value = addTransactionStates.value.copy(
                    isRecurring = addTransactionEvents.state
                )
            }

            // Transaction Name
            is AddTransactionEvents.ChangeTransactionName -> {
                _addTransactionStates.value = addTransactionStates.value.copy(
                    transactionName = addTransactionEvents.name
                )
            }

            // Transaction Currency
            is AddTransactionEvents.ChangeTransactionCurrency -> {
                _addTransactionStates.value = addTransactionStates.value.copy(
                    transactionCurrencyName = addTransactionEvents.currencyName,
                    transactionCurrencyCode = addTransactionEvents.currencyCode,
                    transactionCurrencySymbol = addTransactionEvents.currencySymbol,
                    transactionCurrencyExpanded = addTransactionEvents.currencyExpanded
                )
                Log.d("AddTransactionViewModel","Base Currency ${_addTransactionStates.value.baseCurrencyName}")
                Log.d("AddTransactionViewModel","Transaction Currency ${addTransactionStates.value.transactionCurrencyName}")
                if(addTransactionStates.value.transactionCurrencyName == _addTransactionStates.value.baseCurrencyName){
                    _addTransactionStates.value = addTransactionStates.value.copy(
                        showConversion = false,
                        showExchangeRate = false
                    )
                    Log.d("AddTransactionViewModel","Show Conversion Inside if ${_addTransactionStates.value.showConversion}")
                    Log.d("AddTransactionViewModel","Show Exchange Rate Inside if  ${addTransactionStates.value.showExchangeRate}")
                }
            }
            is AddTransactionEvents.LoadCurrenciesList -> {
                fetchCurrencies()
            }
            is AddTransactionEvents.ShowConversion -> {
                _addTransactionStates.value = addTransactionStates.value.copy(
                    showConversion = addTransactionEvents.showConversion,
                    showExchangeRate = addTransactionEvents.showExchangeRate
                )
            }
            is AddTransactionEvents.SetConvertedTransactionPrice -> {
                if(_addTransactionStates.value.transactionPrice.isEmpty() || _addTransactionStates.value.transactionPrice.isBlank() ){

                    Log.d("AddTransactionViewModel","transaction price: ${_addTransactionStates.value.transactionPrice}")

                    viewModelScope.launch {
                        addTransactionValidationEventChannel.send(
                            AddTransactionValidationEvent.Failure(
                                errorMessage = "Please Enter the Price"
                            )
                        )
                    }
                }
                else{
                    viewModelScope.launch(Dispatchers.IO) {
                        fetchCurrenciesExchangeRates()
                    }
                }
            }
            is AddTransactionEvents.ChangeSearchCurrency -> {
                _addTransactionStates.value = addTransactionStates.value.copy(
                    searchCurrency = addTransactionEvents.currencyName
                )
            }
            // Transaction Type
            is AddTransactionEvents.SelectTransactionType -> {
                _addTransactionStates.value = addTransactionStates.value.copy(
                    transactionType = addTransactionEvents.type
                )
            }

            // Transaction Description
            is AddTransactionEvents.ChangeTransactionDescription -> {
                _addTransactionStates.value = addTransactionStates.value.copy(
                    transactionDescription = addTransactionEvents.description
                )
            }

            // Transaction Price
            is AddTransactionEvents.ChangeTransactionPrice -> {
                _addTransactionStates.value = addTransactionStates.value.copy(
                    transactionPrice = addTransactionEvents.price
                )
                if(_addTransactionStates.value.showExchangeRate){
                    _addTransactionStates.value = addTransactionStates.value.copy(
                        showConversion = true,
                        showExchangeRate = false
                    )
                }
            }

            // Add Transaction
            AddTransactionEvents.AddTransactionTransaction -> {
                addTransactions()
            }
        }
    }

    private suspend fun loadSavedItemList(){
        savedItemUseCasesWrapper.getAllSavedItemLocalUseCase(userUID = uid).collect{
            _addTransactionStates.value = addTransactionStates.value.copy(
                transactionSearchList = it
            )
        }
    }

    private fun addTransactions(){
        viewModelScope.launch(Dispatchers.IO) {
            val nameResult = addTransactionUseCasesWrapper.validateEmptyField(_addTransactionStates.value.transactionName)
            val priceResult = addTransactionUseCasesWrapper.validateTransactionPrice(_addTransactionStates.value.transactionPrice)
            val categoryResult = addTransactionUseCasesWrapper.validateTransactionCategory(_addTransactionStates.value.category)


            if(!nameResult.isSuccessful || !priceResult.isSuccessful || !categoryResult.isSuccessful){
                addTransactionValidationEventChannel.send(
                    AddTransactionValidationEvent.Failure(
                        errorMessage = nameResult.errorMessage ?: priceResult.errorMessage
                        ?: categoryResult.errorMessage
                    )
                )
            }
            else {
                if(_addTransactionStates.value.showExchangeRate || _addTransactionStates.value.showConversion){
                    fetchCurrenciesExchangeRates()
                }

                val transactionCurrencyName = _addTransactionStates.value.transactionCurrencyName
                val transactionCurrencySymbol = _addTransactionStates.value.transactionCurrencySymbol
                val transactionCurrencyCode = _addTransactionStates.value.transactionCurrencyCode

                val selectedTransactionCurrency = Currency(name = transactionCurrencyName, symbol = transactionCurrencySymbol)
                val transactionCurrency: Map<String, Currency> = mapOf(
                    transactionCurrencyCode to selectedTransactionCurrency
                )

                val enteredPrice = _addTransactionStates.value.transactionPrice?.toDoubleOrNull() ?: 0.0
                val convertedPrice = _addTransactionStates.value.convertedPrice?.toDoubleOrNull()

//                Log.d("AddExpenseViewModelA", "Entered Price: $enteredPrice")
//                Log.d("AddExpenseViewModelA", "Converted Price: $convertedPrice")

                val transaction = Transactions(
                    amount = convertedPrice ?: enteredPrice,
                    currency = transactionCurrency,
                    convertedAmount = if (convertedPrice != null) enteredPrice else 0.0,
                    exchangeRate = _addTransactionStates.value.transactionExchangeRate?.toDoubleOrNull() ?: 0.0,
                    transactionType = _addTransactionStates.value.transactionType,
                    category = _addTransactionStates.value.category,
                    dateTime = System.currentTimeMillis(),
                    userUid = uid,
                    description = _addTransactionStates.value.transactionDescription,
                    isRecurring = _addTransactionStates.value.isRecurring,
                    cloudSync = false,
                    transactionName = _addTransactionStates.value.transactionName
                )

//                Log.d("AddExpenseViewModelA", "Transaction: $transaction")

                val isInternetAvailable = addTransactionUseCasesWrapper.internetConnectionAvailability()

                if (cloudSyncStatus) {
                    if (isInternetAvailable) {
                        try {
                            // 1. Insert locally first to generate the ID
                            val rowId = addTransactionUseCasesWrapper.insertNewTransactionsReturnId(transaction)
//                            Log.d("AddExpenseViewModelA", "RowId: $rowId")


                            // 2. Copy the ID into a new transaction object
                            val transactionWithId = transaction.copy(transactionId = rowId.toInt(), cloudSync = true)
//                            Log.d("AddExpenseViewModelA", "TransactionWithId: $transactionWithId")

                            // 3. Save to cloud
                            addTransactionUseCasesWrapper.saveSingleTransactionCloud(userId = uid, transactions = transactionWithId)

                            // 4. Update local record to reflect cloudSync = true
                            addTransactionUseCasesWrapper.insertTransactionsLocally(transactionWithId)

//                            addTransactionUseCasesWrapper.sendBudgetNotificationUseCase(title = "Budget","Your Transaction Added")

                        } catch (e: Exception) {
//                            Log.d("AddExpenseViewModel", "Cloud sync error: ${e.localizedMessage}")

                            try {
                                addTransactionUseCasesWrapper.insertTransactionsLocally(transaction)
                            } catch (e: Exception) {
//                                Log.d("AddExpenseViewModel", "Local save error: ${e.localizedMessage}")
                                addTransactionValidationEventChannel.send(
                                    AddTransactionValidationEvent.Failure(errorMessage = e.localizedMessage)
                                )
                                return@launch
                            }
                        }
                    } else {
                        // No internet, save locally
                        try {
                            addTransactionUseCasesWrapper.insertTransactionsLocally(transaction)
//                            Log.d("AddExpenseViewModel", "Local save No Internet")
                        } catch (e: Exception) {
//                            Log.d("AddExpenseViewModel", "Local save error: ${e.localizedMessage}")
                            addTransactionValidationEventChannel.send(
                                AddTransactionValidationEvent.Failure(errorMessage = e.localizedMessage)
                            )
                            return@launch
                        }
                    }
                } else {
                    // Cloud sync disabled, save locally
                    try {
                        addTransactionUseCasesWrapper.insertTransactionsLocally(transaction)
//                        Log.d("AddExpenseViewModel", "Local save No CloudSync")
                    } catch (e: Exception) {
//                        Log.d("AddExpenseViewModel", "Local save error: ${e.localizedMessage}")
                        addTransactionValidationEventChannel.send(
                            AddTransactionValidationEvent.Failure(errorMessage = e.localizedMessage)
                        )
                        return@launch
                    }
                }
                addTransactionValidationEventChannel.send(AddTransactionValidationEvent.Success)
            }
        }
    }

    private suspend fun fetchCurrenciesExchangeRates(){

            val saveCurrencyMap = setupAccountUseCasesWrapper.getUserProfileFromLocalDb(uid)?.baseCurrency
//            Log.d("AddExpenseViewModel", "saveCurrencyMap: $saveCurrencyMap")
            val baseCurrencyName = saveCurrencyMap?.values?.firstOrNull()?.name ?: "N/A"
            val baseCurrencyCode = saveCurrencyMap?.keys?.firstOrNull() ?: "N/A"
            val baseCurrencySymbol = saveCurrencyMap?.values?.firstOrNull()?.symbol ?: "N/A"
//            Log.d("AddExpenseViewModel", "baseCurrencySymbol: $baseCurrencyCode")
//            Log.d("AddExpenseViewModel", "baseCurrencySymbol: $baseCurrencySymbol")

            val currencyExchangeRate = setupAccountUseCasesWrapper.getCurrencyRatesLocally(baseCurrencyCode)?.conversion_rates
//            Log.d("AddExpenseViewModel", "currencyExchangeRate: $currencyExchangeRate")
            // Check if the map contains the currency code
            val selectedCurrencyRate = if (currencyExchangeRate != null && currencyExchangeRate.containsKey(_addTransactionStates.value.transactionCurrencyCode)) {
                BigDecimal(currencyExchangeRate[_addTransactionStates.value.transactionCurrencyCode] ?: 1.0)
                    .setScale(4, RoundingMode.HALF_UP)
                    .toString()
            } else {
                "1.0000" // Default value with 4 decimal places
            }
//            Log.d("AddExpenseViewModel", "selectedCurrencyRate: $selectedCurrencyRate")

            _addTransactionStates.value = addTransactionStates.value.copy(
                transactionExchangeRate = selectedCurrencyRate
            )

            val priceString = _addTransactionStates.value.transactionPrice
            val rateString = _addTransactionStates.value.transactionExchangeRate
            val price = priceString.toDoubleOrNull() ?: 0.0
            val rate = rateString.toDoubleOrNull() ?: 0.0

//            Log.d("AddExpenseViewModel", "price: $price")
//            Log.d("AddExpenseViewModel", "rate: $rate")

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

                _addTransactionStates.value = addTransactionStates.value.copy(
                    convertedPrice = conversion
                )
            } else {
                // Handle the error if rate is zero or invalid
                _addTransactionStates.value = addTransactionStates.value.copy(
                    convertedPrice = "Error: Invalid rate"
                )
            }

    }

    private fun insertCustomCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val uid = setupAccountUseCasesWrapper.getUIDLocally() ?: "Unknown"
            val category = Category(
                uid = uid,
                name = _addTransactionStates.value.category,
                type = addTransactionStates.value.transactionType.lowercase(),
                icon = "ic_custom",
                isCustom = true
            )
            try{
                addTransactionUseCasesWrapper.insertCustomCategory(category)
                _addTransactionStates.value = addTransactionStates.value.copy(
                    category = ""
                )
            }catch (e: Exception){
//                Log.d("AddTransactionViewModel", "error: ${e.localizedMessage}")
            }
        }
    }

    private fun setTransactionCurrencyToBase(){
        viewModelScope.launch(Dispatchers.IO) {
            val saveCurrencyMap = setupAccountUseCasesWrapper.getUserProfileFromLocalDb(uid)?.baseCurrency
            val baseCurrencyName = saveCurrencyMap?.values?.firstOrNull()?.name ?: "N/A"
            val baseCurrencyCode = saveCurrencyMap?.keys?.firstOrNull() ?: "N/A"
            val baseCurrencySymbol = saveCurrencyMap?.values?.firstOrNull()?.symbol ?: "N/A"

            _addTransactionStates.value = addTransactionStates.value.copy(
                baseCurrencySymbol = baseCurrencySymbol,
                baseCurrencyName = baseCurrencyName,
                baseCurrencyCode = baseCurrencyCode
            )

            _addTransactionStates.value = addTransactionStates.value.copy(
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
                val sortedCountries = setupAccountUseCasesWrapper.getCountryDetailsUseCase()
                    .filter {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() != null
                    }
                    .sortedBy {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() ?: ""
                    }
                    .distinctBy {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() ?: ""
                    }

//                Log.d("AddExpenseViewModel","currencies $sortedCountries")

                _addTransactionStates.value = addTransactionStates.value.copy(
                    currencies = sortedCountries
                )
            } catch (e: Exception) {
                _addTransactionStates.value = addTransactionStates.value.copy(
                    errorMessage = e.localizedMessage ?: " Error Occurred"
                )

                val sortedCountriesLocal = setupAccountUseCasesWrapper.getCountryLocally()
                    .filter {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() != null
                    }
                    .sortedBy {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() ?: ""
                    }
                    .distinctBy {
                        it.currencies?.entries?.firstOrNull()?.value?.name?.lowercase() ?: ""
                    }

                _addTransactionStates.value = addTransactionStates.value.copy(
                    currencies = sortedCountriesLocal
                )

                addTransactionValidationEventChannel.send(AddTransactionValidationEvent.Failure("Error in Fetching Currencies From internet.Using Locally Saved Details"))
            }
        }
    }

    sealed class AddTransactionValidationEvent{
        data object Success: AddTransactionValidationEvent()
        data class Failure(val errorMessage: String?): AddTransactionValidationEvent()
    }

}