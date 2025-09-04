package com.example.financetracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.presentation.features.splash_screen_feature.components.StartUpPageRoot

fun NavGraphBuilder.startUpPageGraph(
    navController: NavController
){
    composable(
        route = Screens.StartUpPageScreen.routes
    ) {
        StartUpPageRoot(navController = navController)
    }
}