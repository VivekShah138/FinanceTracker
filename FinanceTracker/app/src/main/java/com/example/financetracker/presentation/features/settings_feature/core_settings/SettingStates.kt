package com.example.financetracker.presentation.features.settings_feature.core_settings

data class SettingStates(
    val cloudSync: Boolean = false,
    val name: String = "",
    val darkMode: Boolean = false,
    val budgetExist: Boolean = false,
    val userId: String = ""
)