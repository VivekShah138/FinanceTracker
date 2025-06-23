package com.example.financetracker.startup_page_feature.components

data class StartUpPageStates(
    val selectedButton: String = "Register",
    val isLoggedIn: Boolean = false,
    val previousSelectedButton: String? = null
)
