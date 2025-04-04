package com.example.financetracker.main_page_feature.add_transactions.expense.presentation

sealed class AddExpenseEvents {
    data class SelectCategory(val categoryName: String, val bottomSheetState: Boolean, val alertBoxState: Boolean): AddExpenseEvents()
    data class ChangeSavedItemState(val state: Boolean): AddExpenseEvents()
    data class LoadCategory(val type: String): AddExpenseEvents()
    data class ChangeTransactionName(val name: String): AddExpenseEvents()
    data class ChangeTransactionCurrency(val currencyName: String,val currencySymbol: String,val currencyCode: String,val currencyExpanded: Boolean): AddExpenseEvents()
    data object LoadCurrencyRates: AddExpenseEvents()
    data object LoadCurrenciesList: AddExpenseEvents()
    data object SaveCustomCategories: AddExpenseEvents()

    data class ChangeTransactionDescription(val description: String): AddExpenseEvents()
    data class ChangeTransactionQuantity(val quantity: String): AddExpenseEvents()
    data class ChangeTransactionPrice(val price: String): AddExpenseEvents()
//    data object ChangeTransactionFinalPrice: AddExpenseEvents()
    data class ShowConversion(val showConversion: Boolean): AddExpenseEvents()
    data class SetTransactionFinalPrice(val price: String,val quantity: String): AddExpenseEvents()
    data class SetConvertedTransactionPrice(val price: String, val rate:String): AddExpenseEvents()
    data object AddExpenseTransaction: AddExpenseEvents()
}