package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.home_feature.HomePageViewModel
import com.example.financetracker.presentation.features.home_feature.components.HomePageScreen
import com.example.financetracker.presentation.features.settings_feature.SettingsViewModel

fun NavGraphBuilder.homePageGraph(
    navController: NavController,
    settingsViewModel: SettingsViewModel
){
    composable<Screens.HomePageScreen> {
        val viewModel: HomePageViewModel = hiltViewModel()
        HomePageScreen(viewModel, navController, settingsViewModel = settingsViewModel)
    }
}