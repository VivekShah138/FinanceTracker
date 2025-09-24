package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.view_records_feature.components.SingleTransactionScreen

fun NavGraphBuilder.singleSavedItemGraph(
    navController: NavController
){
    composable<Screens.SingleSavedItemScreen> { backStackEntry ->
        val args = backStackEntry.toRoute<Screens.SingleSavedItemScreen>()
        SingleTransactionScreen(
            navController = navController,
            viewTransactionsViewModel = hiltViewModel(),
            transactionId = args.savedItemId
        )
    }
}