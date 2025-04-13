package com.example.financetracker.main_page_feature.view_records.saved_items.presentation

import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems

data class ViewSavedItemsStates(
    val savedItemsList: List<SavedItems> = emptyList()
)
