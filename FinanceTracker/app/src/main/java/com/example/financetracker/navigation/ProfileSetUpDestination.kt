package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.setup_account_feature.ProfileSetUpViewModel
import com.example.financetracker.presentation.features.setup_account_feature.components.ProfileSetUpRoot
import com.example.financetracker.presentation.features.setup_account_feature.components.ProfileSetUpScreen

fun NavGraphBuilder.profileSetUpSGraph(
    navController: NavController
){
    composable<Screens.ProfileSetUpScreen>{
        ProfileSetUpRoot(navController = navController)
    }
}