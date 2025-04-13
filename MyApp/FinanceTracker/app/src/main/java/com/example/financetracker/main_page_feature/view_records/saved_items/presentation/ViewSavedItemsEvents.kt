package com.example.financetracker.main_page_feature.view_records.saved_items.presentation

sealed class ViewSavedItemsEvents() {
    data object LoadTransactions: ViewSavedItemsEvents()
}