package com.example.financetracker.presentation.features.splash_screen_feature

data class StartUpPageStates(
    val selectedButton: String = "Register",
    val isLoggedIn: Boolean = false,
    val previousSelectedButton: String? = null
)
