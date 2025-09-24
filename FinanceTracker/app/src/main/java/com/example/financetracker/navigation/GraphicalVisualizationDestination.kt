package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.presentation.features.charts_feature.ChartsViewModel
import com.example.financetracker.presentation.features.charts_feature.components.ChartsPage

fun NavGraphBuilder.graphicalVisualizationGraph(
    navController: NavController
){
//    composable(
//        route = Screens.GraphicalVisualizationScreen.routes
//    ) {
//
//        val viewModel: ChartsViewModel = hiltViewModel()
//
//        ChartsPage(navController = navController, viewModel = viewModel)
//    }
    composable<Screens.ChartsScreen>{

        val viewModel: ChartsViewModel = hiltViewModel()

        ChartsPage(navController = navController, viewModel = viewModel)
    }
}