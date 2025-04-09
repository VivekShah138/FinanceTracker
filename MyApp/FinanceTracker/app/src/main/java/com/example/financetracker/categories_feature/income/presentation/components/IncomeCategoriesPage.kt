package com.example.financetracker.categories_feature.income.presentation.components

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
import com.example.financetracker.categories_feature.presentation.components.SingleCategoryDisplay

@Composable
fun IncomeCategoriesPage(
    viewModel: IncomeCategoriesViewModel
){
    val states by viewModel.expenseCategoriesState.collectAsStateWithLifecycle()

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
                onClickDelete = {},
                onClickItem = {},
                text = category.name,
                isPredefined = false
            )
        }
    }
}