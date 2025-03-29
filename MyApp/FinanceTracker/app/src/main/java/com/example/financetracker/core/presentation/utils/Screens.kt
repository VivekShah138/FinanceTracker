package com.example.financetracker.core.presentation.utils

sealed class Screens(val routes : String) {

    object RegistrationScreen: Screens("registration_screen")
    object StartUpPageScreen: Screens("startup_page_screen")
    object LogInScreen: Screens("login_screen")
    object ForgotPasswordScreen: Screens("forgot_password_screen")
    object HomePageScreen: Screens("home_page_screen")
    object ProfileSetUpScreen: Screens("profile_setup_screen")
    object NewUserProfileOnBoardingScreen: Screens("new_user_profile_onboarding_screen")
    object ViewTransactionsScreen: Screens("view_transactions_screen")
    object GraphicalVisualizationScreen: Screens("graphical_visualizations_screen")
    object SettingScreen: Screens("setting_screen")
    object AddTransactionsScreen: Screens("add_transactions_screen")
    object AddIncomeTransactionsScreen: Screens("add_income_transactions_screen")
    object AddExpenseTransactionsScreen: Screens("add_expense_transactions_screen")
    object AddSaveItemsTransactionsScreen: Screens("add_save_items_transactions_screen")
}