package com.example.financetracker.navigation.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.financetracker.navigation.addTransactionsGraph
import com.example.financetracker.navigation.budgetGraph
import com.example.financetracker.navigation.categoriesGraph
import com.example.financetracker.navigation.forgotPasswordGraph
import com.example.financetracker.navigation.graphicalVisualizationGraph
import com.example.financetracker.navigation.homePageGraph
import com.example.financetracker.navigation.logInPageGraph
import com.example.financetracker.navigation.newUserProfileOnBoardingGraph
import com.example.financetracker.navigation.profileSetUpSGraph
import com.example.financetracker.navigation.registrationPageGraph
import com.example.financetracker.navigation.settingsGraph
import com.example.financetracker.navigation.singleSavedItemGraph
import com.example.financetracker.navigation.singleTransactionGraph
import com.example.financetracker.navigation.startUpPageGraph
import com.example.financetracker.navigation.viewRecordsGraph
import com.example.financetracker.presentation.features.settings_feature.SettingsViewModel

@Composable
fun FinanceTrackerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    settingsViewModel: SettingsViewModel,
    startDestination: Screens = Screens.StartUpPageScreen
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){
        startUpPageGraph(navController = navController)

        logInPageGraph(navController = navController)

        registrationPageGraph(navController = navController)

        forgotPasswordGraph(navController = navController)

        homePageGraph(
            navController = navController,
            settingsViewModel = settingsViewModel
        )

        profileSetUpSGraph(navController = navController)

        newUserProfileOnBoardingGraph(navController = navController)

        settingsGraph(
            navController = navController,
            settingsViewModel = settingsViewModel
        )

        graphicalVisualizationGraph(navController = navController)

        viewRecordsGraph(navController = navController)

        addTransactionsGraph(navController = navController)

        categoriesGraph(navController = navController)

        budgetGraph(navController = navController)

        singleTransactionGraph(navController = navController)

        singleSavedItemGraph(navController = navController)
    }
}