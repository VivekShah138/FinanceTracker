package com.example.financetracker.main_page_feature.settings.presentation

import com.example.financetracker.setup_account.presentation.ProfileSetUpEvents

sealed class SettingEvents {
    data class ChangeCloudSync(val isChecked: Boolean): SettingEvents()
    data class ChangeDarkMode(val isDarkMode: Boolean): SettingEvents()
    data object LogOut: SettingEvents()
}