package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.local.budget.GetBudgetLocalUseCase
import com.example.financetracker.domain.usecases.remote.transactions.GetRemoteTransactionsList
import com.example.financetracker.domain.usecases.remote.transactions.SaveMultipleTransactionsCloud
import com.example.financetracker.domain.usecases.remote.user_profile.LogoutUseCase
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileFromLocalDb
import com.example.financetracker.domain.usecases.local.shared_pref.GetCloudSyncLocally
import com.example.financetracker.domain.usecases.local.shared_pref.GetDarkModeLocally
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocally
import com.example.financetracker.domain.usecases.local.shared_pref.GetUserNameLocally
import com.example.financetracker.domain.usecases.local.shared_pref.SetCloudSyncLocally
import com.example.financetracker.domain.usecases.local.shared_pref.SetDarkModeLocally
import com.example.financetracker.domain.usecases.local.shared_pref.SetUserNameLocally
import com.example.financetracker.domain.usecases.remote.saved_items.SaveMultipleSavedItemCloud

data class SettingsUseCaseWrapper (
    val getCloudSyncLocally: GetCloudSyncLocally,
    val setCloudSyncLocally: SetCloudSyncLocally,
    val saveMultipleTransactionsCloud: SaveMultipleTransactionsCloud,
    val saveMultipleSavedItemCloud: SaveMultipleSavedItemCloud,
    val logoutUseCase: LogoutUseCase,
    val getUIDLocally: GetUIDLocally,
    val getUserProfileFromLocalDb: GetUserProfileFromLocalDb,
    val setDarkModeLocally: SetDarkModeLocally,
    val getDarkModeLocally: GetDarkModeLocally,
    val setUserNameLocally: SetUserNameLocally,
    val getUserNameLocally: GetUserNameLocally,
    val getRemoteTransactionsList: GetRemoteTransactionsList,
    val getBudgetLocalUseCase: GetBudgetLocalUseCase
)