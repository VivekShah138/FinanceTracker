package com.example.financetracker

import timber.log.Timber

object Logger {


    object Tag {
        const val LOGIN_VIEWMODEL = "LoginViewModel"
        const val REGISTER_VIEWMODEL = "RegisterViewModel"
        const val STARTUP_VIEWMODEL = "StartUpViewModel"
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

    }

    fun d(tag: String, message: String) {
        Timber.tag(tag).d(message)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        Timber.tag(tag).e(throwable, message)
    }
}