package com.example.financetracker.main_page_feature.settings.presentation

data class SettingStates(
    val cloudSync: Boolean = false,
    val name: String = "",
    val darkMode: Boolean = false,
    val budgetExist: Boolean = false,
    val userId: String = ""
)