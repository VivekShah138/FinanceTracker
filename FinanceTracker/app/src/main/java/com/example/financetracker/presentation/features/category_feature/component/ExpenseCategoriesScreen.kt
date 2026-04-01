package com.example.financetracker.presentation.features.category_feature.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financetracker.domain.model.Category
import com.example.financetracker.presentation.features.category_feature.events.SharedCategoriesEvents
import com.example.financetracker.presentation.features.category_feature.states.CategoriesStates
import com.example.financetracker.presentation.features.finance_entry_feature.components.CustomTextAlertBox

@Composable
fun ExpenseCategoriesScreen(
    states: CategoriesStates,
    categoryStates: Category?,
    onEvent: (SharedCategoriesEvents) -> Unit
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {
            Text(
                text = "Predefined Categories",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }


        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 2.dp,
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 4.dp,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    states.predefinedCategories.forEachIndexed { index, category ->
                        Column {
                            SingleCategoryDisplay(

                                onClickDelete = {},
                                onClickItem = {},
                                text = category.name,
                                isPredefined = true
                            )
                            if (index < states.predefinedCategories.lastIndex) {
                                HorizontalDivider(color = MaterialTheme.colorScheme.inverseSurface)
                            }
                        }
                    }
                }
            }
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

        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 2.dp,
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 4.dp,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    states.customCategories.forEachIndexed { index, category ->
                        Column {
                            SingleCategoryDisplay(

                                onClickDelete = {
                                    onEvent(
                                        SharedCategoriesEvents.ChangeSelectedCategory(category)
                                    )
                                    onEvent(
                                        SharedCategoriesEvents.DeleteCategory
                                    )
                                },
                                onClickItem = {
                                    onEvent(
                                        SharedCategoriesEvents.ChangeSelectedCategory(category)
                                    )
                                    onEvent(
                                        SharedCategoriesEvents.ChangeCategoryAlertBoxState(true)
                                    )
                                },
                                text = category.name,
                                isPredefined = false
                            )
                            if (index < states.customCategories.lastIndex) {
                                HorizontalDivider(color = MaterialTheme.colorScheme.inverseSurface)
                            }
                        }
                    }
                }
            }
        }


    }

    if(states.updateCategoryAlertBoxState){
        CustomTextAlertBox(
            selectedCategory = categoryStates?.name ?: "N/A",
            onCategoryChange = {
                onEvent(
                    SharedCategoriesEvents.ChangeCategoryName(it)
                )
            },
            onDismissRequest = {
                onEvent(
                    SharedCategoriesEvents.ChangeCategoryAlertBoxState(state = false)
                )
            },
            onSaveCategory = {
                onEvent(
                    SharedCategoriesEvents.SaveCategory
                )
                onEvent(
                    SharedCategoriesEvents.ChangeCategoryAlertBoxState(state = false)
                )
            },
            title = "Update Custom Category",
            label = "Category Title"
        )
    }

}