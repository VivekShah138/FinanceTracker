package com.example.financetracker.core.presentation.utils

sealed class Screens(val routes : String) {
    object RegistrationScreen: Screens("registration_screen")
    object StartUpPageScreen: Screens("startup_page_screen")
    object LogInScreen: Screens("login_screen")
    object ForgotPasswordScreen: Screens("forgot_password_screen")
    object HomePageScreen: Screens("home_page_screen")
    object SetUpAccountPageScreen: Screens("setup_account_page")
}