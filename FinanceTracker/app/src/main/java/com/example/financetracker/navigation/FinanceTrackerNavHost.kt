package com.example.financetracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.financetracker.presentation.features.settings_feature.SettingsViewModel

@Composable
fun FinanceTrackerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    settingsViewModel: SettingsViewModel,
    startDestination: String = Screens.StartUpPageScreen.routes,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
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