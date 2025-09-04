package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.financetracker.presentation.features.auth_feature.components.RegisterPage
import com.example.financetracker.presentation.features.auth_feature.viewmodels.RegisterPageViewModel
import com.example.financetracker.presentation.features.view_records_feature.components.SingleTransactionScreen

fun NavGraphBuilder.singleTransactionGraph(
    navController: NavController
){
    composable(
        route = Screens.SingleTransactionScreen.routes + "/{transactionId}",
        arguments = listOf(navArgument("transactionId") { type = NavType.IntType })
    ) { backStackEntry ->
        val transactionId = backStackEntry.arguments?.getInt("transactionId")
        SingleTransactionScreen(
            navController = navController,
            viewTransactionsViewModel = hiltViewModel(),
            transactionId = transactionId ?: -1  // fallback if null
        )
    }
}