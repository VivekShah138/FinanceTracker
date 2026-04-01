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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.domain.model.Category
import com.example.financetracker.presentation.features.category_feature.events.SharedCategoriesEvents
import com.example.financetracker.presentation.features.category_feature.states.CategoriesStates
import com.example.financetracker.presentation.features.finance_entry_feature.components.CustomTextAlertBox
import com.example.financetracker.ui.theme.FinanceTrackerTheme


@Composable
fun IncomeCategoriesScreen(
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


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun IncomeCategoriesScreenPreview() {
    FinanceTrackerTheme(darkTheme = true) {

        val dummyPredefinedCategories = listOf(
            Category(
                categoryId = 1,
                uid = "user_123",
                name = "Salary",
                type = "income",
                icon = "ic_salary",
                isCustom = false
            ),
            Category(
                categoryId = 2,
                uid = "user_123",
                name = "Freelance",
                type = "income",
                icon = "ic_freelance",
                isCustom = false
            ),
            Category(
                categoryId = 3,
                uid = "user_123",
                name = "Investments",
                type = "income",
                icon = "ic_investments",
                isCustom = false
            ),
            Category(
                categoryId = 4,
                uid = "user_123",
                name = "Food & Dining",
                type = "expense",
                icon = "ic_food",
                isCustom = false
            ),
            Category(
                categoryId = 5,
                uid = "user_123",
                name = "Transport",
                type = "expense",
                icon = "ic_transport",
                isCustom = false
            ),
            Category(
                categoryId = 6,
                uid = "user_123",
                name = "Shopping",
                type = "expense",
                icon = "ic_shopping",
                isCustom = false
            ),
            Category(
                categoryId = 7,
                uid = "user_123",
                name = "Entertainment",
                type = "expense",
                icon = "ic_entertainment",
                isCustom = false
            ),
            Category(
                categoryId = 8,
                uid = "user_123",
                name = "Health",
                type = "expense",
                icon = "ic_health",
                isCustom = false
            )
        )

        val dummyCustomCategories = listOf(
            Category(
                categoryId = 1,
                uid = "user_123",
                name = "Salary",
                type = "income",
                icon = "ic_salary",
                isCustom = false
            ),
            Category(
                categoryId = 2,
                uid = "user_123",
                name = "Freelance",
                type = "income",
                icon = "ic_freelance",
                isCustom = false
            ),
            Category(
                categoryId = 3,
                uid = "user_123",
                name = "Investments",
                type = "income",
                icon = "ic_investments",
                isCustom = false
            )
        )

        IncomeCategoriesScreen(
            states = CategoriesStates(
                predefinedCategories = dummyPredefinedCategories,
                customCategories = dummyCustomCategories
            ),
            categoryStates = null,
            onEvent = {

            }
        )
    }
}