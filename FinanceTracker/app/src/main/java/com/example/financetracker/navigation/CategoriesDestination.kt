package com.example.financetracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.category_feature.component.CategoriesRoot
import com.example.financetracker.presentation.features.category_feature.component.CategoriesScreen
import com.example.financetracker.presentation.features.category_feature.viewmodel.CoreCategoriesViewModel
import com.example.financetracker.presentation.features.category_feature.viewmodel.ExpenseCategoriesViewModel
import com.example.financetracker.presentation.features.category_feature.viewmodel.IncomeCategoriesViewModel

fun NavGraphBuilder.categoriesGraph(
    navController: NavController
){
    composable<Screens.CategoriesScreen> {
        CategoriesRoot(navController = navController)
    }
}