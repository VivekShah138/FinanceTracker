package com.example.financetracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.settings_feature.SettingsViewModel
import com.example.financetracker.presentation.features.settings_feature.components.SettingsRoot
import com.example.financetracker.presentation.features.settings_feature.components.SettingsScreen

fun NavGraphBuilder.settingsGraph(
    navController: NavController,
    settingsViewModel: SettingsViewModel
){
    composable<Screens.SettingScreen>{
        SettingsRoot(
            navController = navController,
            viewModel = settingsViewModel
        )
    }
}