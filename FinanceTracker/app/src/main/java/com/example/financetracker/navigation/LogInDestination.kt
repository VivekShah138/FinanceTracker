package com.example.financetracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.auth_feature.components.LoginPageRoot

fun NavGraphBuilder.logInPageGraph(
    navController: NavController
){
    composable<Screens.LogInScreen>{
        LoginPageRoot(
            navController = navController
        )
    }
}