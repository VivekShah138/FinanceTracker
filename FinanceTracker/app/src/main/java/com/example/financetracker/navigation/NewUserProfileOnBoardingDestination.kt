package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.presentation.features.auth_feature.components.RegisterPage
import com.example.financetracker.presentation.features.auth_feature.viewmodels.RegisterPageViewModel
import com.example.financetracker.presentation.features.setup_account_feature.ProfileSetUpViewModel
import com.example.financetracker.presentation.features.setup_account_feature.components.NewUserProfileOnBoardingScreens

fun NavGraphBuilder.newUserProfileOnBoardingGraph(
    navController: NavController
){
    composable<Screens.NewUserProfileOnBoardingScreen>{
        val viewModel: ProfileSetUpViewModel = hiltViewModel()
        NewUserProfileOnBoardingScreens(viewModel, navController)
    }
}