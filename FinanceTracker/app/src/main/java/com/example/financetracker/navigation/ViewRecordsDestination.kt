package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.presentation.features.view_records_feature.components.RecordsScreen
import com.example.financetracker.presentation.features.view_records_feature.viewmodels.ViewSavedItemsViewModel
import com.example.financetracker.presentation.features.view_records_feature.viewmodels.ViewTransactionsViewModel
import androidx.navigation.toRoute
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.view_records_feature.components.RecordsRoot

fun NavGraphBuilder.viewRecordsGraph(
    navController: NavController
) {
    composable<Screens.ViewRecordsScreen> { backStackEntry ->
        val args = backStackEntry.toRoute<Screens.ViewRecordsScreen>()
        val viewTransactionsViewModel: ViewTransactionsViewModel = hiltViewModel()
        val viewSavedItemsViewModel: ViewSavedItemsViewModel = hiltViewModel()

        RecordsRoot(
            navController = navController,
            defaultTabIndex = args.tabIndex
        )
    }
}
