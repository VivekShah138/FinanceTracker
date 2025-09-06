package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.presentation.features.auth_feature.components.RegisterPage
import com.example.financetracker.presentation.features.auth_feature.viewmodels.RegisterPageViewModel
import com.example.financetracker.presentation.features.category_feature.component.CategoriesScreen
import com.example.financetracker.presentation.features.category_feature.viewmodel.CoreCategoriesViewModel
import com.example.financetracker.presentation.features.category_feature.viewmodel.ExpenseCategoriesViewModel
import com.example.financetracker.presentation.features.category_feature.viewmodel.IncomeCategoriesViewModel

fun NavGraphBuilder.categoriesGraph(
    navController: NavController
){
    composable<Screens.CategoriesScreen> {

        val expenseCategoriesViewModel: ExpenseCategoriesViewModel = hiltViewModel()
        val incomeCategoriesViewModel: IncomeCategoriesViewModel = hiltViewModel()
        val coreCategoriesViewModel: CoreCategoriesViewModel = hiltViewModel()

        CategoriesScreen(
            navController = navController,
            expenseCategoriesViewModel = expenseCategoriesViewModel,
            incomeCategoriesViewModel = incomeCategoriesViewModel,
            coreCategoriesViewModel = coreCategoriesViewModel
        )
    }
}