package com.example.financetracker.presentation.features.settings_feature

data class SettingStates(
    val cloudSync: Boolean = false,
    val name: String = "",
    val darkMode: Boolean = false,
    val budgetExist: Boolean = false,
    val userId: String = ""
)