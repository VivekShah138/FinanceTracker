package com.example.financetracker.main_page_feature.add_transactions.expense.presentation

import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.setup_account.domain.model.Country

data class AddExpenseStates(

    // Category
    val bottomSheetState: Boolean = false,
    val category: String = "",
    val categoryList: List<Category> = emptyList(),
    val alertBoxState: Boolean = false,

    //SavedItem
    val saveItemState: Boolean = false,

    // Transaction Name
    val transactionName: String = "",

    // BaseCurrency
    val baseCurrencyName: String = "",
    val baseCurrencyCode: String = "",
    val baseCurrencySymbol: String = "",

    // Transaction Currencies List
    val currencies: List<Country> = emptyList(),
    val transactionCurrencyName: String = "",
    val transactionCurrencyCode: String = "",
    val transactionCurrencySymbol: String = "",
    val transactionCurrencyExpanded: Boolean = false,

    // Transaction Description
    val transactionDescription: String = "",

    // Transaction Price
    val transactionPrice: String = "",
    val showConversion: Boolean = false,
    val convertedPrice: String = "",
    val transactionExchangeRate: String = "",

    val isRecurring: Boolean = false,

    val errorMessage: String = ""
)
