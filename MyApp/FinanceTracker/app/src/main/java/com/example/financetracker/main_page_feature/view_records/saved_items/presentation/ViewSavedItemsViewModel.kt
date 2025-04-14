package com.example.financetracker.main_page_feature.view_records.saved_items.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.main_page_feature.view_records.use_cases.ViewRecordsUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewSavedItemsViewModel @Inject constructor(
    private val viewRecordsUseCaseWrapper: ViewRecordsUseCaseWrapper
): ViewModel() {

    private val _viewSavedItemsStates = MutableStateFlow(ViewSavedItemsStates())
    val viewSavedItemsStates : StateFlow<ViewSavedItemsStates> = _viewSavedItemsStates.asStateFlow()

    private val uid = viewRecordsUseCaseWrapper.getUIDLocally() ?: "Unknown"

    init {
        getAllSavedItems()
    }

    fun onEvent(viewSavedItemsEvents: ViewSavedItemsEvents){

        when(viewSavedItemsEvents){
            // Load Saved Items
            is ViewSavedItemsEvents.LoadTransactions -> {
                getAllSavedItems()
            }

            // Delete Selected Saved Items
            is ViewSavedItemsEvents.DeleteSelectedSavedItems -> {
                viewModelScope.launch(Dispatchers.IO) {
                    viewRecordsUseCaseWrapper.deleteSelectedSavedItemsByIdsLocally(_viewSavedItemsStates.value.selectedSavedItems)

                    _viewSavedItemsStates.value = viewSavedItemsStates.value.copy(
                        isSelectionMode = false,
                        selectedSavedItems = emptySet()
                    )
                }

            }

            // Selections
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

}