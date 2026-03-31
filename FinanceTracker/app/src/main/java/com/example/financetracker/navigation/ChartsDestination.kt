package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.charts_feature.ChartsViewModel
import com.example.financetracker.presentation.features.charts_feature.components.ChartsRoot
import com.example.financetracker.presentation.features.charts_feature.components.ChartsScreen

fun NavGraphBuilder.graphicalVisualizationGraph(
    navController: NavController
){
    composable<Screens.ChartsScreen>{

        val viewModel: ChartsViewModel = hiltViewModel()

        ChartsRoot()
    }
}