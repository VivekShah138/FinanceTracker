package com.example.financetracker.main_page_feature.view_records.saved_items.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.main_page_feature.view_records.ViewRecordsUseCaseWrapper
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