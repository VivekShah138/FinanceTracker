package com.example.financetracker.presentation.features.splash_screen_feature

sealed class StartUpPageEvents(){

    data class ChangeSelectedButton(val button: String): StartUpPageEvents()
    data class ChangePreviousSelectedButton(val button: String): StartUpPageEvents()

}
