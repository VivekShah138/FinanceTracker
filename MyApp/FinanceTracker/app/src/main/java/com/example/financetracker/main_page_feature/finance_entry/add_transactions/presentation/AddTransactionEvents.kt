package com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation

sealed class AddTransactionEvents {
    data class SelectCategory(val categoryName: String, val bottomSheetState: Boolean, val alertBoxState: Boolean): AddTransactionEvents()
    data class ChangeSavedItemState(val state: Boolean): AddTransactionEvents()
    data class ChangeRecurringItemState(val state: Boolean): AddTransactionEvents()
    data class LoadCategory(val type: String): AddTransactionEvents()
    data class ChangeTransactionName(val name: String): AddTransactionEvents()
    data class ChangeTransactionCurrency(val currencyName: String,val currencySymbol: String,val currencyCode: String,val currencyExpanded: Boolean): AddTransactionEvents()
    data object LoadCurrencyRates: AddTransactionEvents()
    data object LoadCurrenciesList: AddTransactionEvents()
    data object SaveCustomCategories: AddTransactionEvents()
    data class SelectTransactionType(val type: String,val expanded:Boolean): AddTransactionEvents()
    data class ChangeTransactionDescription(val description: String): AddTransactionEvents()
    data class ChangeTransactionPrice(val price: String): AddTransactionEvents()
    data class ShowConversion(val showConversion: Boolean): AddTransactionEvents()
    data class SetConvertedTransactionPrice(val price: String, val rate:String): AddTransactionEvents()
    data object AddTransactionTransaction: AddTransactionEvents()
}