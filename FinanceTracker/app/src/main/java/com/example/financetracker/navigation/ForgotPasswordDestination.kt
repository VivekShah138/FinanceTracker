package com.example.financetracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.auth_feature.components.ForgotPasswordRoot
fun NavGraphBuilder.forgotPasswordGraph(
    navController: NavController
){
    composable<Screens.ForgotPasswordScreen>{
        ForgotPasswordRoot(navController = navController)
    }
}