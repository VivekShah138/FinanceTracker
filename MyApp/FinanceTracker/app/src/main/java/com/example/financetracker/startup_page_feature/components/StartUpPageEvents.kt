package com.example.financetracker.startup_page_feature.components

sealed class StartUpPageEvents(){

    data class ChangeSelectedButton(val button: String): StartUpPageEvents()
    data class ChangePreviousSelectedButton(val button: String): StartUpPageEvents()

}
