package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.auth_feature.components.ForgotPasswordPage
import com.example.financetracker.presentation.features.auth_feature.viewmodels.ForgotPasswordViewModel

fun NavGraphBuilder.forgotPasswordGraph(
    navController: NavController
){
    composable<Screens.ForgotPasswordScreen>{
        val viewModel: ForgotPasswordViewModel = hiltViewModel()
        ForgotPasswordPage(navController, viewModel)
    }
}