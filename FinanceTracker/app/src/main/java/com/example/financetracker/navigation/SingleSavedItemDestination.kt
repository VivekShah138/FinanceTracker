package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.financetracker.presentation.features.auth_feature.components.RegisterPage
import com.example.financetracker.presentation.features.auth_feature.viewmodels.RegisterPageViewModel
import com.example.financetracker.presentation.features.view_records_feature.components.SingleSavedItemScreen

fun NavGraphBuilder.singleSavedItemGraph(
    navController: NavController
){
    composable(
        route = Screens.SingleSavedItemScreen.routes + "/{savedItemId}",
        arguments = listOf(navArgument("savedItemId") { type = NavType.IntType })
    ) { backStackEntry ->
        val savedItemId = backStackEntry.arguments?.getInt("savedItemId")
        SingleSavedItemScreen(
            navController = navController,
            viewSavedItemsViewModel = hiltViewModel(),
            savedItemId = savedItemId ?: -1
        )
    }
}