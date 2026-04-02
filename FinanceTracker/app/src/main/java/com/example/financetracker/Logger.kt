package com.example.financetracker

import timber.log.Timber

object Logger {


    object Tag {
        const val LOGIN_VIEWMODEL = "LoginViewModel"
        const val CORE_CATEGORY_VIEWMODEL = "CoreCategoryViewModel"
        const val INCOME_CATEGORY_VIEWMODEL = "IncomeCategoryViewModel"
        const val EXPENSE_CATEGORY_VIEWMODEL = "ExpenseCategoryViewModel"
        const val CHARTS_VIEWMODEL = "ChartsViewModel"
        const val STARTUP_VIEWMODEL = "StartUpViewModel"
        const val ADD_SAVED_ITEM_VIEWMODEL = "AddSavedItemViewModel"
        const val ADD_TRANSACTION_VIEWMODEL = "AddTransactionViewModel"
        const val HOME_VIEWMODEL = "HomeViewModel"
        const val SETTING_VIEWMODEL = "SettingViewModel"
        const val PROFILE_SETUP_ACCOUNT_VIEWMODEL = "ProfileSetUpAccountViewModel"
        const val STARTUP_SCREEN = "StartUpScreen"
        const val APP_ENTRY = "AppEntry"
        const val MAIN_ACTIVITY = "MainActivity"
        const val DELETE_REMOTE_TRANSACTIONS_TO_REMOTE_WORK_MANAGER = "DeleteRemoteTransactionsRemotelyWorkManager"
        const val DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER = "DeleteRemoteSavedItemsRemotelyWorkManager"
        const val INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER = "InsertBudgetsToLocalWorkManager"
        const val INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER = "InsertTransactionsToLocalWorkManager"
        const val INSERT_SAVED_ITEMS_TO_LOCAL_WORK_MANAGER = "InsertSavedItemsToLocalWorkManager"
        const val INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER = "InsertCategoryToLocalWorkManager"
        const val INSERT_COUNTRY_TO_LOCAL_WORK_MANAGER = "InsertCountryToLocalWorkManager"
        const val INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER = "InsertCurrencyRatesToLocalWorkManager"
        const val INSERT_USER_PROFILE_TO_LOCAL_WORK_MANAGER = "InsertUserProfileToLocalWorkManager"
        const val INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER = "InsertBudgetsToRemoteWorkManager"
        const val INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER = "InsertTransactionsToRemoteWorkManager"
        const val INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER = "InsertSavedItemsToRemoteWorkManager"
        const val JSON_CATEGORY_MAPPER = "JsonCategoryMapper"
        const val ACCOUNT_MANAGER = "AccountManager"

    }

    fun d(tag: String, message: String) {
        Timber.tag(tag).d(message)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        Timber.tag(tag).e(throwable, message)
    }
}