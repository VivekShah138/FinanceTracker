package com.example.financetracker.main_page_feature.settings.domain.use_cases

import com.example.financetracker.core.cloud.domain.usecase.SaveMultipleTransactionsCloud
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetCloudSyncLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetCloudSyncLocally
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.SaveMultipleSavedItemCloud

data class SettingsUseCaseWrapper (
    val getCloudSyncLocally: GetCloudSyncLocally,
    val setCloudSyncLocally: SetCloudSyncLocally,
    val saveMultipleTransactionsCloud: SaveMultipleTransactionsCloud,
    val saveMultipleSavedItemCloud: SaveMultipleSavedItemCloud
)