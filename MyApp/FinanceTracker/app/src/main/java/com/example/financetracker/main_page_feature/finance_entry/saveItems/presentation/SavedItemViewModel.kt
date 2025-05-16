package com.example.financetracker.main_page_feature.finance_entry.saveItems.presentation

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionViewModel.AddTransactionValidationEvent
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.SavedItemsUseCasesWrapper
import com.example.financetracker.setup_account.domain.model.Currency
import com.example.financetracker.setup_account.domain.usecases.SetupAccountUseCasesWrapper
import com.google.gson.JsonParseException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SavedItemViewModel @Inject constructor(
    private val setupAccountUseCasesWrapper: SetupAccountUseCasesWrapper,
    private val savedItemsUseCasesWrapper: SavedItemsUseCasesWrapper
): ViewModel() {

    private val _savedItemsState = MutableStateFlow(SavedItemsStates())
    val savedItemsState: StateFlow<SavedItemsStates> = _savedItemsState.asStateFlow()

    private val savedItemsValidationEventChannel = Channel<AddTransactionValidationEvent>()
    val savedItemsValidationEvents = savedItemsValidationEventChannel.receiveAsFlow()

    private val userUID = setupAccountUseCasesWrapper.getUIDLocally() ?: "Unknown"
    private val cloudSyncStatus = savedItemsUseCasesWrapper.getCloudSyncLocally()

    init {
        loadInitialCurrency()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun onEvent(savedItemsEvent: SavedItemsEvent) {
        when (savedItemsEvent) {
            SavedItemsEvent.LoadCurrencies -> {
                viewModelScope.launch {
                    fetchBaseCurrencies()
                }

            }
            is SavedItemsEvent.OnChangeItemCurrency -> {
                _savedItemsState.value = savedItemsState.value.copy(
                    itemCurrencyName = savedItemsEvent.name,
                    itemCurrencySymbol = savedItemsEvent.symbol,
                    itemCurrencyCode = savedItemsEvent.code,
                    itemCurrencyExpanded = savedItemsEvent.expanded
                )
            }
            is SavedItemsEvent.OnChangeItemDescription -> {
                _savedItemsState.value = savedItemsState.value.copy(
                    itemDescription = savedItemsEvent.description,
                )
            }
            is SavedItemsEvent.OnChangeItemName -> {
                _savedItemsState.value = savedItemsState.value.copy(
                    itemName = savedItemsEvent.name,
                )
            }
            is SavedItemsEvent.OnChangeItemPrice -> {
                _savedItemsState.value = savedItemsState.value.copy(
                    itemPrice = savedItemsEvent.price,
                )
            }
            is SavedItemsEvent.OnChangeItemShopName -> {
                _savedItemsState.value = savedItemsState.value.copy(
                    itemShopName = savedItemsEvent.shopeName,
                )
            }

            SavedItemsEvent.Submit -> {
                viewModelScope.launch {
                    saveItems()
                }
            }
        }
    }


    private suspend fun fetchBaseCurrencies() {
        withContext(Dispatchers.IO) {
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

                _savedItemsState.value = savedItemsState.value.copy(
                    itemCurrenciesList = sortedCurrencies
                )

            } catch (e: IOException) {
                Log.e("SavedItemViewModel", "Network Error: ${e.localizedMessage}", e)
                // Handle network error (no internet, connection issue, etc.)

            } catch (e: HttpException) {
                Log.e("SavedItemViewModel", "HTTP Error: ${e.localizedMessage}, Code:", e)
                // Handle HTTP error (non-2xx responses, etc.)

            } catch (e: JsonParseException) {
                Log.e("SavedItemViewModel", "JSON Parse Error: ${e.localizedMessage}", e)
                // Handle JSON parsing errors (e.g., malformed JSON)

            } catch (e: Exception) {
                Log.e("SavedItemViewModel", "Unexpected Error: ${e.localizedMessage}", e)
                // Handle any other unexpected errors

                Log.d("SavedItemViewModel","Country Error: ${e.localizedMessage}")

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
                    savedItemsValidationEventChannel.send(AddTransactionValidationEvent.Failure("Error in Fetching Currencies From internet.Try Again Later"))
                }
                else{
                    _savedItemsState.value = savedItemsState.value.copy(
                        itemCurrenciesList = sortedCurrenciesLocally
                    )

                    Log.d("SavedItemViewModel","currencies ${_savedItemsState.value.itemCurrenciesList}")

                    savedItemsValidationEventChannel.send(AddTransactionValidationEvent.Failure("Error in Fetching Currencies From internet.Using Locally Saved Details"))
                }

            }
        }
    }

    private suspend fun saveItems() {
        withContext(Dispatchers.IO){

            val itemNameResult = savedItemsUseCasesWrapper.savedItemsValidationUseCase(
                stateName = "Item Name",
                state = _savedItemsState.value.itemName
            )
            val itemPriceResult = savedItemsUseCasesWrapper.savedItemsValidationUseCase(
                stateName = "Item Price",
                state = _savedItemsState.value.itemPrice
            )

            if(!itemPriceResult.isSuccessful || !itemNameResult.isSuccessful){
//                val errorToSend = itemPriceResult.errorMessage ?: itemNameResult.errorMessage ?: "Unknown error"

                val errorToSend = listOf(itemPriceResult.errorMessage, itemNameResult.errorMessage)
                    .filterNot { it.isNullOrBlank() }
                    .firstOrNull() ?: "Unknown error"
                savedItemsValidationEventChannel.send(AddTransactionValidationEvent.Failure(errorToSend))
            }
            else{

                val itemCurrencyCode = _savedItemsState.value.itemCurrencyCode
                val itemCurrencyName = _savedItemsState.value.itemCurrencyName
                val itemCurrencySymbol = _savedItemsState.value.itemCurrencySymbol

                // Create the Currency object
                val selectedCurrency = Currency(name = itemCurrencyName, symbol = itemCurrencySymbol)


                // Create the baseCurrency map with the code as key and the map as value
                val itemCurrency: Map<String, Currency> = mapOf(
                    itemCurrencyCode to selectedCurrency  // Map the code to the map of currency details
                )

                val savedItem = SavedItems(
                    itemName = _savedItemsState.value.itemName,
                    itemPrice = _savedItemsState.value.itemPrice.toDoubleOrNull() ?: 0.0,
                    itemDescription = _savedItemsState.value.itemDescription,
                    itemShopName = _savedItemsState.value.itemShopName,
                    itemCurrency = itemCurrency,
                    userUID = userUID,
                    cloudSync = false
                )

                val isInternetAvailable = savedItemsUseCasesWrapper.internetConnectionAvailability()


                if (cloudSyncStatus) {
                    if (isInternetAvailable) {
                        try {
                            // 1. Insert locally first to generate the ID
                            val rowId = savedItemsUseCasesWrapper.saveNewItemReturnId(savedItems = savedItem)

//                            Log.d("SavedItemViewModel", "savedItemId $rowId")

                            // 2. Copy the ID into a new transaction object
                            val savedItemWithId = savedItem.copy(itemId = rowId.toInt(), cloudSync = true)

//                            Log.d("SavedItemViewModel", "savedItemWithId $savedItemWithId")

                            // 3. Save to cloud
                            savedItemsUseCasesWrapper.saveSingleSavedItemCloud(userId = userUID, savedItems = savedItemWithId)

                            // 4. Update local record to reflect cloudSync = true
                            savedItemsUseCasesWrapper.saveItemLocalUseCase(savedItems = savedItemWithId)


                        } catch (e: Exception) {
//                            Log.d("AddExpenseViewModel", "Cloud sync error: ${e.localizedMessage}")

                            try {
                                savedItemsUseCasesWrapper.saveItemLocalUseCase(savedItems = savedItem)
                            } catch (e: Exception) {
//                                Log.d("AddExpenseViewModel", "Local save error: ${e.localizedMessage}")
                                savedItemsValidationEventChannel.send(
                                    AddTransactionValidationEvent.Failure(errorMessage = e.localizedMessage)
                                )
                                return@withContext
                            }
                        }
                    } else {
                        // No internet, save locally
                        try {
                            savedItemsUseCasesWrapper.saveItemLocalUseCase(savedItems = savedItem)
//                            Log.d("AddExpenseViewModel", "Local save No Internet")
                        } catch (e: Exception) {
//                            Log.d("AddExpenseViewModel", "Local save error: ${e.localizedMessage}")
                            savedItemsValidationEventChannel.send(
                                AddTransactionValidationEvent.Failure(errorMessage = e.localizedMessage)
                            )
                            return@withContext
                        }
                    }
                } else {
                    // Cloud sync disabled, save locally
                    try {
                        savedItemsUseCasesWrapper.saveItemLocalUseCase(savedItems = savedItem)
//                        Log.d("AddExpenseViewModel", "Local save No CloudSync")
                    } catch (e: Exception) {
//                        Log.d("AddExpenseViewModel", "Local save error: ${e.localizedMessage}")
                        savedItemsValidationEventChannel.send(
                            AddTransactionValidationEvent.Failure(errorMessage = e.localizedMessage)
                        )
                        return@withContext
                    }
                }
                savedItemsValidationEventChannel.send(AddTransactionValidationEvent.Success)

            }
        }
    }

    private fun loadInitialCurrency(){
        viewModelScope.launch(Dispatchers.IO){
            val userProfile = setupAccountUseCasesWrapper.getUserProfileFromLocalDb(userUID)

            if(userProfile != null){
                val baseCurrency = userProfile.baseCurrency

                val itemCurrencyName = baseCurrency.entries?.firstOrNull()?.value?.name ?: "N/A"
                val itemCurrencySymbol = baseCurrency.entries?.firstOrNull()?.value?.symbol ?: "N/A"
                val itemCurrencyCode = baseCurrency.entries?.firstOrNull()?.key ?: "N/A"

                _savedItemsState.value = savedItemsState.value.copy(
                    itemCurrencyName = itemCurrencyName,
                    itemCurrencyCode = itemCurrencyCode,
                    itemCurrencySymbol = itemCurrencySymbol
                )
            }
            else{
                savedItemsValidationEventChannel.send(AddTransactionValidationEvent.Failure("Failed to load base currency"))
            }
        }
    }

}