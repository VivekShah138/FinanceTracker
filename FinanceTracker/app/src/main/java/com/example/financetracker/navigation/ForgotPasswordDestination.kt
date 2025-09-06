package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.presentation.features.auth_feature.components.ForgotPasswordPage
import com.example.financetracker.presentation.features.auth_feature.components.LogInPage
import com.example.financetracker.presentation.features.auth_feature.components.RegisterPage
import com.example.financetracker.presentation.features.auth_feature.viewmodels.ForgotPasswordViewModel
import com.example.financetracker.presentation.features.auth_feature.viewmodels.LoginPageViewModel
import com.example.financetracker.presentation.features.auth_feature.viewmodels.RegisterPageViewModel
import com.example.financetracker.presentation.features.splash_screen_feature.components.StartUpPageRoot

fun NavGraphBuilder.forgotPasswordGraph(
    navController: NavController
){
//    composable(
//        route = Screens.ForgotPasswordScreen.routes
//    ) {
//        val viewModel: ForgotPasswordViewModel = hiltViewModel()
//        ForgotPasswordPage(navController, viewModel)
//    }
    composable<Screens.ForgotPasswordScreen>{
        val viewModel: ForgotPasswordViewModel = hiltViewModel()
        ForgotPasswordPage(navController, viewModel)
    }
}