package com.example.financetracker.categories_feature.expense.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker.categories_feature.core.presentation.SharedCategoriesEvents
import com.example.financetracker.categories_feature.expense.presentation.ExpenseCategoriesViewModel
import com.example.financetracker.categories_feature.core.presentation.components.SingleCategoryDisplay
import com.example.financetracker.main_page_feature.finance_entry.finance_entry_core.presentation.components.CustomTextAlertBox

@Composable
fun ExpenseCategoriesPage(
    viewModel: ExpenseCategoriesViewModel
){
    val states by viewModel.expenseCategoriesState.collectAsStateWithLifecycle()
    val categoryStates by viewModel.categoryState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Predefined Section
        item {
            Text(
                text = "Predefined Categories",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        items(states.predefinedCategories) { category ->
            SingleCategoryDisplay(
                onClickDelete = {},
                onClickItem = {},
                text = category.name,
                isPredefined = true
            )
        }

        // Custom Section
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Custom Categories",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        items(states.customCategories) { category ->
            SingleCategoryDisplay(
                onClickDelete = {
                    viewModel.onEvent(
                        SharedCategoriesEvents.ChangeSelectedCategory(category)
                    )
                    viewModel.onEvent(
                        SharedCategoriesEvents.DeleteCategory
                    )
                },
                onClickItem = {
                    Log.d("ExpenseCategoriesPage","category: $category")
                    viewModel.onEvent(
                        SharedCategoriesEvents.ChangeSelectedCategory(category)
                    )

                    viewModel.onEvent(
                        SharedCategoriesEvents.ChangeCategoryAlertBoxState(true)
                    )
                },
                text = category.name,
                isPredefined = false
            )
        }
    }

    // if view alert box is true
    if(states.updateCategoryAlertBoxState){
        CustomTextAlertBox(
            selectedCategory = categoryStates?.name ?: "N/A",
            onCategoryChange = {
                viewModel.onEvent(
                    SharedCategoriesEvents.ChangeCategoryName(it)
                )
            },
            onDismissRequest = {
                viewModel.onEvent(
                    SharedCategoriesEvents.ChangeCategoryAlertBoxState(state = false)
                )
            },
            onSaveCategory = {
                viewModel.onEvent(
                    SharedCategoriesEvents.SaveCategory
                )
                viewModel.onEvent(
                    SharedCategoriesEvents.ChangeCategoryAlertBoxState(state = false)
                )
            },
            title = "Update Custom Category",
            label = "Category Title"
        )
    }

}