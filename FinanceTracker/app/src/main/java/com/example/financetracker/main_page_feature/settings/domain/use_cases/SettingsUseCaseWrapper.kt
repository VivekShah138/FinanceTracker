package com.example.financetracker.main_page_feature.settings.domain.use_cases

import com.example.financetracker.budget_feature.domain.usecases.GetBudgetLocalUseCase
import com.example.financetracker.core.cloud.domain.usecase.GetRemoteTransactionsList
import com.example.financetracker.core.cloud.domain.usecase.GetUserUIDUseCase
import com.example.financetracker.core.cloud.domain.usecase.SaveMultipleTransactionsCloud
import com.example.financetracker.core.core_domain.usecase.LogoutUseCase
import com.example.financetracker.core.local.domain.room.usecases.GetUserProfileFromLocalDb
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetCloudSyncLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetDarkModeLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetUIDLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetUserNameLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetCloudSyncLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetDarkModeLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetUserNameLocally
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.SaveMultipleSavedItemCloud

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