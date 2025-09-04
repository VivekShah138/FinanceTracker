package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.presentation.features.auth_feature.components.RegisterPage
import com.example.financetracker.presentation.features.auth_feature.viewmodels.RegisterPageViewModel
import com.example.financetracker.presentation.features.budget_feature.BudgetViewModel
import com.example.financetracker.presentation.features.budget_feature.components.BudgetScreen

fun NavGraphBuilder.budgetGraph(
    navController: NavController
){
    composable(
        route = Screens.BudgetScreen.routes
    ) {
        val budgetViewModel: BudgetViewModel = hiltViewModel()

        BudgetScreen(
            navController = navController,
            budgetViewModel = budgetViewModel
        )
    }
}