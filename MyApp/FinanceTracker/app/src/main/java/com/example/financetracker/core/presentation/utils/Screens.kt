package com.example.financetracker.core.presentation.utils

sealed class Screens(val routes : String) {
    object RegistrationScreen: Screens("registration_screen")
    object StartUpPageScreen: Screens("startup_page_screen")
    object LogInScreen: Screens("login_screen")
    object ForgotPasswordScreen: Screens("forgot_password_screen")
    object HomePageScreen: Screens("home_page_screen")
    object ProfileSetUp: Screens("profile_setup_screen")
    object NewUserProfileOnBoardingScreens: Screens("new_user_profile_onboarding_screen")
}