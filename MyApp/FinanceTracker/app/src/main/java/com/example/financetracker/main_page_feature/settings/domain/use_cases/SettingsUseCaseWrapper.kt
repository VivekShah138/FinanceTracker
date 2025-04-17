package com.example.financetracker.main_page_feature.settings.domain.use_cases

import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetCloudSyncLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetCloudSyncLocally

data class SettingsUseCaseWrapper (
    val getCloudSyncLocally: GetCloudSyncLocally,
    val setCloudSyncLocally: SetCloudSyncLocally
)