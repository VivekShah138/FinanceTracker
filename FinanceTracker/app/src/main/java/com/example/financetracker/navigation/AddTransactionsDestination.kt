package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.presentation.features.auth_feature.components.RegisterPage
import com.example.financetracker.presentation.features.auth_feature.viewmodels.RegisterPageViewModel
import com.example.financetracker.presentation.features.finance_entry_feature.components.FinanceEntryPage
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.SavedItemViewModel

fun NavGraphBuilder.addTransactionsGraph(
    navController: NavController
){
//    composable(
//        route = Screens.AddTransactionsScreen.routes
//    ) {
//        val addTransactionViewModel: AddTransactionViewModel = hiltViewModel()
//        val savedItemViewModel: SavedItemViewModel = hiltViewModel()
//        FinanceEntryPage(
//            navController = navController,
//            addTransactionViewModel = addTransactionViewModel,
//            savedItemViewModel = savedItemViewModel
//        )
//    }
    composable<Screens.AddTransactionsScreen> {
        val addTransactionViewModel: AddTransactionViewModel = hiltViewModel()
        val savedItemViewModel: SavedItemViewModel = hiltViewModel()
        FinanceEntryPage(
            navController = navController,
            addTransactionViewModel = addTransactionViewModel,
            savedItemViewModel = savedItemViewModel
        )
    }
}