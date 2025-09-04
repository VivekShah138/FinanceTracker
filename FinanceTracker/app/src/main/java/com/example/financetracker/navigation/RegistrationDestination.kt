package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.presentation.features.auth_feature.components.RegisterPage
import com.example.financetracker.presentation.features.auth_feature.viewmodels.RegisterPageViewModel

fun NavGraphBuilder.registrationPageGraph(
    navController: NavController
){
    composable(
        route = Screens.RegistrationScreen.routes
    ) {
        val viewModel: RegisterPageViewModel = hiltViewModel()
        RegisterPage(navController, viewModel)
    }
}