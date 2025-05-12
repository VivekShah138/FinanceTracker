package com.example.financetracker.main_page_feature.view_records.transactions.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.categories_feature.core.presentation.components.SingleCategoryDisplay
import com.example.financetracker.categories_feature.core.presentation.components.SingleCategoryDisplay2
import com.example.financetracker.ui.theme.AppTheme


//@Composable
//fun Practice() {
//    Scaffold { padding ->
//
//        val list = listOf(1,2,3,4,5,6,7,8,9,10)
//
////        Surface(
////            modifier = Modifier
////                .fillMaxSize()
////                .padding(16.dp),
////            tonalElevation = 2.dp,
////            shape = MaterialTheme.shapes.medium,
////            shadowElevation = 4.dp,
////            color = MaterialTheme.colorScheme.surface
////        ) {
//            LazyColumn(
//                modifier = Modifier.fillMaxSize()
//            ) {
//
//                    // Predefined Section Header
//                    item {
//                        Text(
//                            text = "Predefined Categories",
//                            style = MaterialTheme.typography.titleMedium,
//                            modifier = Modifier.padding(vertical = 8.dp)
//                        )
//                    }
//
//
//                // Predefined Categories with Divider
//                itemsIndexed(list) { index, category ->
//                    Column(
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Surface(
//                            modifier = Modifier
//                                .fillMaxSize(),
////                                .padding(16.dp),
////                            tonalElevation = 2.dp,
//                            shape = MaterialTheme.shapes.medium,
////                            shadowElevation = 4.dp,
//                            color = MaterialTheme.colorScheme.primaryContainer
//                        ) {
//
//                            SingleCategoryDisplay2(
//                                onClickDelete = {},
//                                onClickItem = {},
////                        text = category.name,
//                                text = list.toString(),
//                                isPredefined = true
//                            )
////                    if (index < states.predefinedCategories.lastIndex) {
////                        Divider()
////                    }
//                            if (index < list.lastIndex) {
//                                Divider()
//                            }
//
//                        }
//                    }
//
//                }
//
//                // Custom Section Header
//                item {
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Text(
//                        text = "Custom Categories",
//                        style = MaterialTheme.typography.titleMedium,
//                        modifier = Modifier.padding(vertical = 8.dp)
//                    )
//                }
//
//                // Custom Categories with Divider
//                itemsIndexed(list) { index, category ->
//                    SingleCategoryDisplay2(
//                        onClickDelete = {
////                            viewModel.onEvent(
////                                SharedCategoriesEvents.ChangeSelectedCategory(category)
////                            )
////                            viewModel.onEvent(
////                                SharedCategoriesEvents.DeleteCategory
////                            )
//                        },
//                        onClickItem = {
////                            Log.d("ExpenseCategoriesPage", "category: $category")
////                            viewModel.onEvent(
////                                SharedCategoriesEvents.ChangeSelectedCategory(category)
////                            )
////                            viewModel.onEvent(
////                                SharedCategoriesEvents.ChangeCategoryAlertBoxState(true)
////                            )
//                        },
////                        text = category.name,
//                        text = list.toString(),
//                        isPredefined = false
//                    )
////                    if (index < states.customCategories.lastIndex) {
////                        Divider()
////                    }
//                    if (index < list.lastIndex) {
//                        Divider()
//                    }
//                }
//            }
//        }
//
//
////    }
//}
@Composable
fun Practice() {
    Scaffold { padding ->
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Section title (outside the surface)
            item {
                Text(
                    text = "Predefined Categories",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // All items inside one surface
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 2.dp,
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 4.dp,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        list.forEachIndexed { index, category ->
                            Column {
                                SingleCategoryDisplay2(
                                    onClickDelete = {},
                                    onClickItem = {},
                                    text = category.toString(),
                                    isPredefined = true
                                )
                                if (index < list.lastIndex) {
                                    Divider()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}





@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PracticePreview(){

    AppTheme(dynamicColor = false) {

        Practice()

    }

}