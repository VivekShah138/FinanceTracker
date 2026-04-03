package com.example.financetracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.home_feature.components.HomeRoot
import com.example.financetracker.presentation.features.settings_feature.core_settings.SettingsViewModel

fun NavGraphBuilder.homePageGraph(
    navController: NavController,
    settingsViewModel: SettingsViewModel
){
    composable<Screens.HomePageScreen> {
        HomeRoot(navController = navController, settingsViewModel = settingsViewModel)
    }
}