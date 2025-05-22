package com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation

import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems

sealed class AddTransactionEvents {

    // Category
    data class SelectCategory(val categoryName: String, val bottomSheetState: Boolean, val alertBoxState: Boolean): AddTransactionEvents()
    data class LoadCategory(val type: String): AddTransactionEvents()
    data object SaveCustomCategories: AddTransactionEvents()

    // SavedItem
    data class ChangeSavedItemState(val state: Boolean): AddTransactionEvents()
    data class ChangeSavedItemSearchState(val state: Boolean): AddTransactionEvents()
    data object LoadSavedItemList: AddTransactionEvents()
    data class FilterSavedItemList(val list: List<SavedItems>, val newWord: String): AddTransactionEvents()
    data class ChangeQuantity(val state: Boolean): AddTransactionEvents()
    data class CalculateFinalPrice(val quantity: Int,val price: Double): AddTransactionEvents()
    data class ChangeSelectedItem(val item: SavedItems): AddTransactionEvents()

    // Recurring Item
    data class ChangeRecurringItemState(val state: Boolean): AddTransactionEvents()

    // Transaction Name
    data class ChangeTransactionName(val name: String): AddTransactionEvents()

    // Transaction Currency
    data class ChangeTransactionCurrency(val currencyName: String,val currencySymbol: String,val currencyCode: String,val currencyExpanded: Boolean): AddTransactionEvents()
    data object LoadCurrenciesList: AddTransactionEvents()
    data class ShowConversion(val showConversion: Boolean, val showExchangeRate: Boolean): AddTransactionEvents()
    data class SetConvertedTransactionPrice(val price: String, val rate:String): AddTransactionEvents()
    data class ChangeSearchCurrency(val currencyName: String): AddTransactionEvents()

    // Transaction Type
    data class SelectTransactionType(val type: String): AddTransactionEvents()

    // Transaction Description
    data class ChangeTransactionDescription(val description: String): AddTransactionEvents()

    // Transaction Price
    data class ChangeTransactionPrice(val price: String): AddTransactionEvents()

    // Adding Transaction
    data object AddTransactionTransaction: AddTransactionEvents()
}