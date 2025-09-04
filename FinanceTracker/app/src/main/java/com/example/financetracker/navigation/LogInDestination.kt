package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.presentation.features.auth_feature.components.LogInPage
import com.example.financetracker.presentation.features.auth_feature.viewmodels.LoginPageViewModel

fun NavGraphBuilder.logInPageGraph(
    navController: NavController
){
    composable(
        route = Screens.LogInScreen.routes
    ) {
        val viewModel: LoginPageViewModel = hiltViewModel()
        LogInPage(navController, viewModel)
    }
}