package com.example.financetracker.navigation

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.financetracker.presentation.features.auth_feature.components.RegisterPage
import com.example.financetracker.presentation.features.auth_feature.viewmodels.RegisterPageViewModel
import com.example.financetracker.presentation.features.view_records_feature.components.RecordsPage
import com.example.financetracker.presentation.features.view_records_feature.viewmodels.ViewSavedItemsViewModel
import com.example.financetracker.presentation.features.view_records_feature.viewmodels.ViewTransactionsViewModel

fun NavGraphBuilder.viewRecordsGraph(
    navController: NavController
){
    composable(
        route = "${Screens.ViewRecordsScreen.routes}/{tabIndex}",
        arguments = listOf(navArgument("tabIndex") { type = NavType.IntType; defaultValue = 0 })
    ) { backStackEntry ->
        val tabIndex = backStackEntry.arguments?.getInt("tabIndex") ?: 0
        val viewTransactionsViewModel: ViewTransactionsViewModel = hiltViewModel()
        val viewSavedItemsViewModel: ViewSavedItemsViewModel = hiltViewModel()

        Log.d("RecordsPage", "pagerTabIndex $tabIndex")
        RecordsPage(
            navController = navController,
            viewSavedItemsViewModel = viewSavedItemsViewModel,
            viewTransactionsViewModel = viewTransactionsViewModel,
            defaultTabIndex = tabIndex
        )
    }
}