package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.local.budget.GetBudgetLocalUseCase
import com.example.financetracker.domain.usecases.remote.transactions.GetAllTransactionsRemoteUseCase
import com.example.financetracker.domain.usecases.remote.transactions.InsertTransactionsRemoteUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.LogoutUseCase
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileFromLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetCloudSyncLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetDarkModeLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetUserNameLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetCloudSyncLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetDarkModeLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetUserNameLocalUseCase
import com.example.financetracker.domain.usecases.remote.saved_items.SaveMultipleSavedItemCloud

data class SettingsUseCaseWrapper (
    val getCloudSyncLocalUseCase: GetCloudSyncLocalUseCase,
    val setCloudSyncLocalUseCase: SetCloudSyncLocalUseCase,
    val insertTransactionsRemoteUseCase: InsertTransactionsRemoteUseCase,
    val saveMultipleSavedItemCloud: SaveMultipleSavedItemCloud,
    val logoutUseCase: LogoutUseCase,
    val getUIDLocalUseCase: GetUIDLocalUseCase,
    val getUserProfileFromLocalUseCase: GetUserProfileFromLocalUseCase,
    val setDarkModeLocalUseCase: SetDarkModeLocalUseCase,
    val getDarkModeLocalUseCase: GetDarkModeLocalUseCase,
    val setUserNameLocalUseCase: SetUserNameLocalUseCase,
    val getUserNameLocalUseCase: GetUserNameLocalUseCase,
    val getAllTransactionsRemoteUseCase: GetAllTransactionsRemoteUseCase,
    val getBudgetLocalUseCase: GetBudgetLocalUseCase
)