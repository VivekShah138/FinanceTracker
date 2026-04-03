package com.example.financetracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.settings_feature.app_info.component.AppInfoScreen

fun NavGraphBuilder.appInfoDestination(
    navController: NavController,
){
    composable<Screens.AppInfoScreen> {
        AppInfoScreen(navController = navController)
    }

}