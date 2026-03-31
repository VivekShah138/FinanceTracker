package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.finance_entry_feature.components.AddFinanceRoot
import com.example.financetracker.presentation.features.finance_entry_feature.components.AddFinanceScreen
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddSavedItemViewModel

fun NavGraphBuilder.addTransactionsGraph(
    navController: NavController
){
    composable<Screens.AddTransactionsScreen> {
        AddFinanceRoot(navController = navController)
    }
}