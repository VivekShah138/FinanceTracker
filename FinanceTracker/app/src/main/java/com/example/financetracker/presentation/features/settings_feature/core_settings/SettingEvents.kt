package com.example.financetracker.presentation.features.settings_feature.core_settings

sealed class SettingEvents {
    data class ChangeCloudSync(val isChecked: Boolean): SettingEvents()
    data class ChangeDarkMode(val isDarkMode: Boolean): SettingEvents()
    data object LogOut: SettingEvents()
}