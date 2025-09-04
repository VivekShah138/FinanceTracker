package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.presentation.features.auth_feature.components.RegisterPage
import com.example.financetracker.presentation.features.auth_feature.viewmodels.RegisterPageViewModel
import com.example.financetracker.presentation.features.home_feature.HomePageViewModel
import com.example.financetracker.presentation.features.home_feature.components.HomePageScreen
import com.example.financetracker.presentation.features.settings_feature.SettingsViewModel

fun NavGraphBuilder.homePageGraph(
    navController: NavController,
    settingsViewModel: SettingsViewModel
){
    composable(
        route = Screens.HomePageScreen.routes
    ) {
        val viewModel: HomePageViewModel = hiltViewModel()
        HomePageScreen(viewModel, navController, settingsViewModel = settingsViewModel)
    }
}