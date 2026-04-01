package com.example.financetracker.presentation.features.finance_entry_feature.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.Logger
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel.AddTransactionValidationEvent
import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.domain.usecases.usecase_wrapper.SavedItemsUseCasesWrapper
import com.example.financetracker.domain.model.Currency
import com.example.financetracker.domain.usecases.usecase_wrapper.SetupAccountUseCasesWrapper
import com.example.financetracker.presentation.features.finance_entry_feature.events.AddSavedItemsEvents
import com.example.financetracker.presentation.features.finance_entry_feature.states.AddSavedItemsStates
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
class AddSavedItemViewModel @Inject constructor(
    private val setupAccountUseCasesWrapper: SetupAccountUseCasesWrapper,
    private val savedItemsUseCasesWrapper: SavedItemsUseCasesWrapper
): ViewModel() {

    private val _savedItemsState = MutableStateFlow(AddSavedItemsStates())
    val savedItemsState: StateFlow<AddSavedItemsStates> = _savedItemsState.asStateFlow()

    private val savedItemsValidationEventChannel = Channel<AddTransactionValidationEvent>()
    val savedItemsValidationEvents = savedItemsValidationEventChannel.receiveAsFlow()

    private val userUID = setupAccountUseCasesWrapper.getUIDLocalUseCase() ?: "Unknown"
    private val cloudSyncStatus = savedItemsUseCasesWrapper.getCloudSyncLocalUseCase()

    init {
        loadInitialCurrency()
    }

    fun onEvent(addSavedItemsEvents: AddSavedItemsEvents) {
        when (addSavedItemsEvents) {
            AddSavedItemsEvents.LoadCurrencies -> {
                viewModelScope.launch {
                    fetchBaseCurrencies()
                }

            }
            is AddSavedItemsEvents.OnChangeItemCurrency -> {
                _savedItemsState.value = savedItemsState.value.copy(
                    itemCurrencyName = addSavedItemsEvents.name,
                    itemCurrencySymbol = addSavedItemsEvents.symbol,
                    itemCurrencyCode = addSavedItemsEvents.code,
                    itemCurrencyExpanded = addSavedItemsEvents.expanded
                )
            }
            is AddSavedItemsEvents.OnChangeItemDescription -> {
                _savedItemsState.value = savedItemsState.value.copy(
                    itemDescription = addSavedItemsEvents.description,
                )
            }
            is AddSavedItemsEvents.OnChangeItemName -> {
                _savedItemsState.value = savedItemsState.value.copy(
                    itemName = addSavedItemsEvents.name,
                )
            }
            is AddSavedItemsEvents.OnChangeItemPrice -> {
                _savedItemsState.value = savedItemsState.value.copy(
                    itemPrice = addSavedItemsEvents.price,
                )
            }
            is AddSavedItemsEvents.OnChangeItemShopName -> {
                _savedItemsState.value = savedItemsState.value.copy(
                    itemShopName = addSavedItemsEvents.shopName,
                )
            }

            AddSavedItemsEvents.Submit -> {
                viewModelScope.launch {
                    saveItems()
                }
            }
        }
    }


    private suspend fun fetchBaseCurrencies() {
        withContext(Dispatchers.IO) {
            try {
                val sortedCurrencies = setupAccountUseCasesWrapper.getCountryDetailsRemoteUseCase()
                    .filter {
                        it.currencies.entries.firstOrNull()?.value?.name?.lowercase() != null
                    }
                    .sortedBy {
                        it.currencies.entries.firstOrNull()?.value?.name ?: "N/A"
                    }
                    .distinctBy {
                        it.currencies.entries.firstOrNull()?.value?.name ?: "N/A"
                    }

                _savedItemsState.value = savedItemsState.value.copy(
                    itemCurrenciesList = sortedCurrencies
                )

            } catch (e: Exception) {
                Logger.e(Logger.Tag.ADD_SAVED_ITEM_VIEWMODEL, "fetchBaseCurrencies -> error in fetching",e)

                val sortedCurrenciesLocally = setupAccountUseCasesWrapper.getCountryLocalUseCase()
                    .filter {
                        it.currencies.entries.firstOrNull()?.value?.name?.lowercase() != null
                    }
                    .sortedBy {
                        it.currencies.entries.firstOrNull()?.value?.name ?: "N/A"
                    }
                    .distinctBy {
                        it.currencies.entries.firstOrNull()?.value?.name ?: "N/A"
                    }

                if(sortedCurrenciesLocally.isEmpty()){
                    savedItemsValidationEventChannel.send(AddTransactionValidationEvent.Failure("Error in Fetching Currencies From internet.Try Again Later"))
                }
                else{
                    _savedItemsState.value = savedItemsState.value.copy(
                        itemCurrenciesList = sortedCurrenciesLocally
                    )

                    Logger.d(Logger.Tag.ADD_SAVED_ITEM_VIEWMODEL, "fetchBaseCurrencies -> currencies ${_savedItemsState.value.itemCurrenciesList}")


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
                            val rowId = savedItemsUseCasesWrapper.insertSavedItemAndReturnIdLocalUseCase(savedItems = savedItem)
                            val savedItemWithId = savedItem.copy(itemId = rowId.toInt(), cloudSync = true)
                            savedItemsUseCasesWrapper.saveSingleSavedItemCloud(userId = userUID, savedItems = savedItemWithId)
                            savedItemsUseCasesWrapper.insertSavedItemLocalUseCase(savedItems = savedItemWithId)


                        } catch (e: Exception) {
                            try {
                                savedItemsUseCasesWrapper.insertSavedItemLocalUseCase(savedItems = savedItem)
                            } catch (e: Exception) {
                                savedItemsValidationEventChannel.send(
                                    AddTransactionValidationEvent.Failure(errorMessage = e.localizedMessage)
                                )
                                return@withContext
                            }
                        }
                    } else {
                        try {
                            savedItemsUseCasesWrapper.insertSavedItemLocalUseCase(savedItems = savedItem)
                        } catch (e: Exception) {
                            savedItemsValidationEventChannel.send(
                                AddTransactionValidationEvent.Failure(errorMessage = e.localizedMessage)
                            )
                            return@withContext
                        }
                    }
                } else {
                    try {
                        savedItemsUseCasesWrapper.insertSavedItemLocalUseCase(savedItems = savedItem)
                    } catch (e: Exception) {
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
            val userProfile = setupAccountUseCasesWrapper.getUserProfileFromLocalUseCase(userUID)

            if(userProfile != null){
                val baseCurrency = userProfile.baseCurrency

                val itemCurrencyName = baseCurrency.entries.firstOrNull()?.value?.name ?: "N/A"
                val itemCurrencySymbol = baseCurrency.entries.firstOrNull()?.value?.symbol ?: "N/A"
                val itemCurrencyCode = baseCurrency.entries.firstOrNull()?.key ?: "N/A"

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