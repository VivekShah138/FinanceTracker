package com.example.financetracker.categories_feature.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.PrimaryKey
import com.example.financetracker.core.core_presentation.components.AppTopBar

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CategoriesScreenPreview(

) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Categories",
                showBackButton = true,
                showMenu = false,
                onBackClick = {

                }
            )
        }

    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

        }
    }
}