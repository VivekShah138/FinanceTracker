package com.example.financetracker.core.core_presentation.utils

sealed class Screens(val routes : String) {

    data object RegistrationScreen: Screens("registration_screen")
    data object StartUpPageScreen: Screens("startup_page_screen")
    data object LogInScreen: Screens("login_screen")
    data object ForgotPasswordScreen: Screens("forgot_password_screen")
    data object HomePageScreen: Screens("home_page_screen")
    data object ProfileSetUpScreen: Screens("profile_setup_screen")
    data object NewUserProfileOnBoardingScreen: Screens("new_user_profile_onboarding_screen")
    data object ViewRecordsScreen: Screens("view_records_screen")
    data object GraphicalVisualizationScreen: Screens("graphical_visualizations_screen")
    data object SettingScreen: Screens("setting_screen")
    data object AddTransactionsScreen: Screens("add_transactions_screen")
    data object CategoriesScreen: Screens("categories_screen")
    data object BudgetScreen: Screens("budget_screen")
    data object SingleTransactionScreen: Screens("single_transaction_screen")
    data object SingleSavedItemScreen: Screens("single_saved_item_screen")
}