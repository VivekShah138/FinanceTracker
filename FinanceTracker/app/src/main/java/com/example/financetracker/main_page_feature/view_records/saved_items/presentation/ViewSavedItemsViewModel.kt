package com.example.financetracker.main_page_feature.view_records.saved_items.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.DeletedTransactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionEvents
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionViewModel.AddTransactionValidationEvent
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.DeletedSavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsEvents
import com.example.financetracker.main_page_feature.view_records.use_cases.ViewRecordsUseCaseWrapper
import com.example.financetracker.setup_account.domain.model.Currency
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
class ViewSavedItemsViewModel @Inject constructor(
    private val viewRecordsUseCaseWrapper: ViewRecordsUseCaseWrapper
): ViewModel() {

    private val _viewSavedItemsStates = MutableStateFlow(ViewSavedItemsStates())
    val viewSavedItemsStates : StateFlow<ViewSavedItemsStates> = _viewSavedItemsStates.asStateFlow()

    private val _savedItemState = MutableStateFlow<SavedItems?>(null)
    val savedItemState : StateFlow<SavedItems?> = _savedItemState.asStateFlow()

    private val savedItemsValidationEventChannel = Channel<AddTransactionValidationEvent>()
    val savedItemsValidationEvents = savedItemsValidationEventChannel.receiveAsFlow()

    private val uid = viewRecordsUseCaseWrapper.getUIDLocally() ?: "Unknown"
    private val cloudSyncStatus = viewRecordsUseCaseWrapper.getCloudSyncLocally()

    init {
        getAllSavedItems()
    }

    fun onEvent(viewSavedItemsEvents: ViewSavedItemsEvents){

        when(viewSavedItemsEvents){
            // Load Saved Items
            is ViewSavedItemsEvents.LoadTransactions -> {
                getAllSavedItems()
            }

            is ViewSavedItemsEvents.ChangeSearchSavedItem -> {

                _viewSavedItemsStates.value = viewSavedItemsStates.value.copy(
                    savedItem = viewSavedItemsEvents.name
                )
                _viewSavedItemsStates.value = viewSavedItemsStates.value.copy(
                    savedItemsFilteredList = emptyList()
                )

            }
            is ViewSavedItemsEvents.FilterSavedItemList -> {

                if(viewSavedItemsEvents.newWord.isEmpty()){
                    getAllSavedItems()
                }else{
                    val filterList = viewSavedItemsEvents.list.filter {
                        it.itemName.contains(
                            viewSavedItemsEvents.newWord,
                            ignoreCase = true
                        )
                    }
                    _viewSavedItemsStates.value = viewSavedItemsStates.value.copy(
                        savedItemsFilteredList = filterList
                    )
                }
            }

            // Delete Selected Saved Items
            is ViewSavedItemsEvents.ChangeCustomDateAlertBox -> {
                _viewSavedItemsStates.value = viewSavedItemsStates.value.copy(
                    customDeleteAlertBoxState = viewSavedItemsEvents.state
                )
            }

            is ViewSavedItemsEvents.DeleteSelectedSavedItems -> {
                viewModelScope.launch(Dispatchers.IO) {

                    val selectedSingleSavedItem = _savedItemState.value?.itemId?.let { mutableSetOf(it) }

                    val selectedIds = if(_viewSavedItemsStates.value.selectedSavedItems.isEmpty()) selectedSingleSavedItem else _viewSavedItemsStates.value.selectedSavedItems

                    selectedIds!!.forEach { selectedSavedItemId ->

                        val selectedSavedItem = viewRecordsUseCaseWrapper.getSavedItemById(selectedSavedItemId)

                        // If Item is Saved In Cloud Then Add it to deleted from cloud
                        if(selectedSavedItem.cloudSync){
                            viewRecordsUseCaseWrapper.insertDeletedSavedItemLocally(
                                DeletedSavedItems(
                                    itemId = selectedSavedItemId,
                                    userUID = uid
                                )
                            )
                        }

                        // Delete Saved Item Locally
                        viewRecordsUseCaseWrapper.deleteSelectedSavedItemsByIdsLocally(selectedSavedItemId)
                    }

                    _viewSavedItemsStates.value = viewSavedItemsStates.value.copy(
                        isSelectionMode = false,
                        selectedSavedItems = emptySet()
                    )
                }
            }

            // Transaction Selection
            is ViewSavedItemsEvents.EnterSelectionMode -> {
                _viewSavedItemsStates.value = viewSavedItemsStates.value.copy(
                    isSelectionMode = true
                )

            }
            is ViewSavedItemsEvents.ExitSelectionMode -> {
                _viewSavedItemsStates.value = viewSavedItemsStates.value.copy(
                    isSelectionMode = false,
                    selectedSavedItems = emptySet()
                )
            }
            is ViewSavedItemsEvents.ToggleSavedItemSelection -> {

                val current = _viewSavedItemsStates.value.selectedSavedItems.toMutableSet()
                if(current.contains(viewSavedItemsEvents.savedItemsId)){
                    current.remove(viewSavedItemsEvents.savedItemsId)
                }
                else{
                    current.add(viewSavedItemsEvents.savedItemsId)
                }

                _viewSavedItemsStates.value = viewSavedItemsStates.value.copy(
                    selectedSavedItems = current
                )

                Log.d("ViewSavedItemsViewModel","SelectedTransaction List: $current")
                Log.d("ViewSavedItemsViewModel","SelectedTransaction state List: ${_viewSavedItemsStates.value.selectedSavedItems}")
                Log.d("ViewSavedItemsViewModel","SelectedTransaction state List size: ${_viewSavedItemsStates.value.selectedSavedItems.size}")
            }

            // Edit Selected SavedItem
            is ViewSavedItemsEvents.ChangeUpdateBottomSheetState -> {
                _viewSavedItemsStates.value = viewSavedItemsStates.value.copy(
                    updateBottomSheetState = viewSavedItemsEvents.state
                )

                updateItemStates()

            }
            is ViewSavedItemsEvents.ChangeSavedItem -> {
                _viewSavedItemsStates.value = viewSavedItemsStates.value.copy(
                    updatedItemName = viewSavedItemsEvents.itemName,
                    updatedItemPrice = viewSavedItemsEvents.itemPrice,
                    updatedItemDescription = viewSavedItemsEvents.itemDescription,
                    updatedItemShopName = viewSavedItemsEvents.itemShopName
                )
            }
            is ViewSavedItemsEvents.SelectSavedItems -> {

                Log.d("ViewSavedItemsViewModel","SavedItems Before ${_savedItemState.value}")
                _savedItemState.value = viewSavedItemsEvents.savedItems
                Log.d("ViewSavedItemsViewModel","SavedItems Before ${_savedItemState.value}")

            }
            is ViewSavedItemsEvents.SaveItem -> {
                viewModelScope.launch {
                    saveItems()
                }
            }

            ViewSavedItemsEvents.SelectAllItems -> {

                val selectedItems = _viewSavedItemsStates.value.selectedSavedItems.toMutableSet()

                _viewSavedItemsStates.value.savedItemsList.forEach { savedItem ->
                    selectedItems.add(savedItem.itemId ?: 0)
                }

                _viewSavedItemsStates.value = _viewSavedItemsStates.value.copy(
                    selectedSavedItems = selectedItems
                )
            }

            is ViewSavedItemsEvents.GetSingleSavedItem -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val savedItem = viewRecordsUseCaseWrapper.getSavedItemById(viewSavedItemsEvents.id)

                    _savedItemState.value = savedItem
                }
            }

            is ViewSavedItemsEvents.RefreshUpdatedItem -> {

                viewModelScope.launch(Dispatchers.IO) {

                    val itemId = _savedItemState.value!!.itemId ?: 0
                    val savedItem = viewRecordsUseCaseWrapper.getSavedItemById(itemId)
                    _savedItemState.value = savedItem
                }

            }
        }
    }


    private fun getAllSavedItems(){
        viewModelScope.launch(Dispatchers.IO) {
            viewRecordsUseCaseWrapper.getAllSavedItemLocalUseCase(uid).collect { savedItems ->
                _viewSavedItemsStates.value = viewSavedItemsStates.value.copy(
                    savedItemsList = savedItems
                )
            }
            Log.d("ViewTransactionsViewModel","transaction List ${_viewSavedItemsStates.value.savedItemsList}")
        }
    }

    private fun updateItemStates(){
        viewModelScope.launch(Dispatchers.IO) {

            val selectedSingleSavedItem = _savedItemState.value?.itemId?.let { mutableSetOf(it) }

            val itemId = if(_viewSavedItemsStates.value.selectedSavedItems.isEmpty()) selectedSingleSavedItem else _viewSavedItemsStates.value.selectedSavedItems
//            val itemId = _viewSavedItemsStates.value.selectedSavedItems

            itemId!!.forEach { savedItemId ->

                val savedItems = viewRecordsUseCaseWrapper.getSavedItemById(savedItemId)
                val itemName = savedItems.itemName
                val itemDescription = savedItems.itemDescription ?: "N/A"
                val itemShopName =savedItems.itemShopName ?: "N/A"
                val itemPrice = savedItems.itemPrice ?: 0.0
                val itemCurrencyName = savedItems.itemCurrency.entries.firstOrNull()?.value?.name ?: "N/A"
                val itemCurrencyCode = savedItems.itemCurrency.entries.firstOrNull()?.key ?: "N/A"
                val itemCurrencySymbol = savedItems.itemCurrency.entries.firstOrNull()?.value?.symbol ?: "N/A"
                val currentItemId = savedItemId

                Log.d("ViewSavedItemsViewModel", "ItemId Before ${_viewSavedItemsStates.value.updatedItemId}")
                Log.d("ViewSavedItemsViewModel", "ItemName Before ${_viewSavedItemsStates.value.updatedItemName}")
                Log.d("ViewSavedItemsViewModel", "ItemDescription Before ${_viewSavedItemsStates.value.updatedItemDescription}")
                Log.d("ViewSavedItemsViewModel", "ItemShopName Before ${_viewSavedItemsStates.value.updatedItemShopName}")
                Log.d("ViewSavedItemsViewModel", "ItemPrice Before ${_viewSavedItemsStates.value.updatedItemPrice}")
                Log.d("ViewSavedItemsViewModel", "ItemCurrencyName Before ${_viewSavedItemsStates.value.updatedItemCurrencyName}")

                _viewSavedItemsStates.value = viewSavedItemsStates.value.copy(
                    updatedItemName = itemName,
                    updatedItemPrice = itemPrice.toString(),
                    updatedItemDescription = itemDescription,
                    updatedItemShopName = itemShopName,
                    updatedItemCurrencyName = itemCurrencyName,
                    updatedItemCurrencySymbol = itemCurrencySymbol,
                    updatedItemCurrencyCode = itemCurrencyCode,
                    updatedItemId = currentItemId
                )

                Log.d("ViewSavedItemsViewModel", "ItemId After ${_viewSavedItemsStates.value.updatedItemId}")
                Log.d("ViewSavedItemsViewModel", "ItemName After ${_viewSavedItemsStates.value.updatedItemName}")
                Log.d("ViewSavedItemsViewModel", "ItemDescription After ${_viewSavedItemsStates.value.updatedItemDescription}")
                Log.d("ViewSavedItemsViewModel", "ItemShopName After ${_viewSavedItemsStates.value.updatedItemShopName}")
                Log.d("ViewSavedItemsViewModel", "ItemPrice After ${_viewSavedItemsStates.value.updatedItemPrice}")
                Log.d("ViewSavedItemsViewModel", "ItemCurrencyName After ${_viewSavedItemsStates.value.updatedItemCurrencyName}")

            }
        }
    }


    private suspend fun saveItems() {
        withContext(Dispatchers.IO){

            val itemNameResult = viewRecordsUseCaseWrapper.savedItemsValidationUseCase(
                stateName = "Item Name",
                state = _viewSavedItemsStates.value.updatedItemName
            )
            val itemPriceResult = viewRecordsUseCaseWrapper.savedItemsValidationUseCase(
                stateName = "Item Price",
                state = _viewSavedItemsStates.value.updatedItemPrice
            )

            if(!itemPriceResult.isSuccessful || !itemNameResult.isSuccessful){
                savedItemsValidationEventChannel.send(AddTransactionValidationEvent.Failure(itemPriceResult.errorMessage ?: itemNameResult.errorMessage))
            }
            else{

                val itemCurrencyCode = _viewSavedItemsStates.value.updatedItemCurrencyCode
                val itemCurrencyName = _viewSavedItemsStates.value.updatedItemCurrencyName
                val itemCurrencySymbol = _viewSavedItemsStates.value.updatedItemCurrencySymbol

                // Create the Currency object
                val selectedCurrency = Currency(name = itemCurrencyName, symbol = itemCurrencySymbol)


                // Create the baseCurrency map with the code as key and the map as value
                val itemCurrency: Map<String, Currency> = mapOf(
                    itemCurrencyCode to selectedCurrency  // Map the code to the map of currency details
                )

                val savedItem = SavedItems(
                    itemId = _viewSavedItemsStates.value.updatedItemId,
                    itemName = _viewSavedItemsStates.value.updatedItemName,
                    itemPrice = _viewSavedItemsStates.value.updatedItemPrice.toDoubleOrNull() ?: 0.0,
                    itemDescription = _viewSavedItemsStates.value.updatedItemDescription,
                    itemShopName = _viewSavedItemsStates.value.updatedItemShopName,
                    itemCurrency = itemCurrency,
                    userUID = uid,
                    cloudSync = false
                )
                Log.d("SavedItemViewModel", "Saved Item $savedItem")

                val isInternetAvailable = viewRecordsUseCaseWrapper.internetConnectionAvailability()


                if (cloudSyncStatus) {
                    if (isInternetAvailable) {
                        try {

                            // 2. Copy the ID into a new transaction object
                            val savedItemWithId = savedItem.copy(cloudSync = true)

                            Log.d("SavedItemViewModel", "savedItemWithId $savedItemWithId")

                            // 3. Save to cloud
                            viewRecordsUseCaseWrapper.saveSingleSavedItemCloud(userId = uid, savedItems = savedItemWithId)

                            // 4. Update local record to reflect cloudSync = true
                            viewRecordsUseCaseWrapper.saveItemLocalUseCase(savedItems = savedItemWithId)


                        } catch (e: Exception) {
                            Log.d("AddExpenseViewModel", "Cloud sync error: ${e.localizedMessage}")

                            try {
                                viewRecordsUseCaseWrapper.saveItemLocalUseCase(savedItems = savedItem)
                            } catch (e: Exception) {
                                Log.d("AddExpenseViewModel", "Local save error: ${e.localizedMessage}")
                                savedItemsValidationEventChannel.send(
                                    AddTransactionValidationEvent.Failure(errorMessage = e.localizedMessage)
                                )
                                return@withContext
                            }
                        }
                    } else {
                        // No internet, save locally
                        try {
                            viewRecordsUseCaseWrapper.saveItemLocalUseCase(savedItems = savedItem)
                            Log.d("AddExpenseViewModel", "Local save No Internet")
                        } catch (e: Exception) {
                            Log.d("AddExpenseViewModel", "Local save error: ${e.localizedMessage}")
                            savedItemsValidationEventChannel.send(
                                AddTransactionValidationEvent.Failure(errorMessage = e.localizedMessage)
                            )
                            return@withContext
                        }
                    }
                } else {
                    // Cloud sync disabled, save locally
                    try {
                        viewRecordsUseCaseWrapper.saveItemLocalUseCase(savedItems = savedItem)
                        Log.d("AddExpenseViewModel", "Local save No CloudSync")
                    } catch (e: Exception) {
                        Log.d("AddExpenseViewModel", "Local save error: ${e.localizedMessage}")
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
}