package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.presentation.features.auth_feature.components.RegisterPage
import com.example.financetracker.presentation.features.auth_feature.viewmodels.RegisterPageViewModel
import com.example.financetracker.presentation.features.settings_feature.SettingsViewModel
import com.example.financetracker.presentation.features.settings_feature.components.SettingsPage

fun NavGraphBuilder.settingsGraph(
    navController: NavController,
    settingsViewModel: SettingsViewModel
){
    composable(
        route = Screens.SettingScreen.routes
    ) {
        SettingsPage(navController = navController, viewModel = settingsViewModel)
    }
}