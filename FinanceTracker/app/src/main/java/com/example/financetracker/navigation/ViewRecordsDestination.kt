package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.presentation.features.view_records_feature.components.RecordsPage
import com.example.financetracker.presentation.features.view_records_feature.viewmodels.ViewSavedItemsViewModel
import com.example.financetracker.presentation.features.view_records_feature.viewmodels.ViewTransactionsViewModel

//fun NavGraphBuilder.viewRecordsGraph(
//    navController: NavController
//){
//    composable(
//        route = "${Screens.ViewRecordsScreen.routes}/{tabIndex}",
//        arguments = listOf(navArgument("tabIndex") { type = NavType.IntType; defaultValue = 0 })
//    ) { backStackEntry ->
//        val tabIndex = backStackEntry.arguments?.getInt("tabIndex") ?: 0
//        val viewTransactionsViewModel: ViewTransactionsViewModel = hiltViewModel()
//        val viewSavedItemsViewModel: ViewSavedItemsViewModel = hiltViewModel()
//
//        Log.d("RecordsPage", "pagerTabIndex $tabIndex")
//        RecordsPage(
//            navController = navController,
//            viewSavedItemsViewModel = viewSavedItemsViewModel,
//            viewTransactionsViewModel = viewTransactionsViewModel,
//            defaultTabIndex = tabIndex
//        )
//    }
//}

import androidx.navigation.toRoute

fun NavGraphBuilder.viewRecordsGraph(
    navController: NavController
) {
    composable<Screens.ViewRecordsScreen> { backStackEntry ->
        val args = backStackEntry.toRoute<Screens.ViewRecordsScreen>()
        val viewTransactionsViewModel: ViewTransactionsViewModel = hiltViewModel()
        val viewSavedItemsViewModel: ViewSavedItemsViewModel = hiltViewModel()

        RecordsPage(
            navController = navController,
            viewSavedItemsViewModel = viewSavedItemsViewModel,
            viewTransactionsViewModel = viewTransactionsViewModel,
            defaultTabIndex = args.tabIndex
        )
    }
}
