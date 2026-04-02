package com.example.financetracker.navigation.core

import kotlinx.serialization.Serializable

sealed interface Screens{
    @Serializable
    data object RegistrationScreen: Screens

    @Serializable
    data object StartUpPageScreen: Screens

    @Serializable
    data object LogInScreen: Screens

    @Serializable
    data object ForgotPasswordScreen: Screens

    @Serializable
    data object HomePageScreen: Screens

    @Serializable
    data object ProfileSetUpScreen: Screens

    @Serializable
    data object NewUserProfileOnBoardingScreen: Screens

    @Serializable
    data class ViewRecordsScreen(val tabIndex: Int = 0): Screens

    @Serializable
    data object ChartsScreen: Screens

    @Serializable
    data object SettingScreen: Screens

    @Serializable
    data object AddTransactionsScreen: Screens

    @Serializable
    data object CategoriesScreen: Screens

    @Serializable
    data object BudgetScreen: Screens

    @Serializable
    data class SingleTransactionScreen(val transactionId: Int): Screens

    @Serializable
    data class SingleSavedItemScreen(val savedItemId: Int): Screens
    data object HelpAndFeedbackScreen: Screens
    data object FeedbackScreen: Screens
    data object AppInfoScreen: Screens
    data object TermsAndPrivacyPolicyScreen: Screens
}