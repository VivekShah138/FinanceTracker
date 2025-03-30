package com.example.financetracker.main_page_feature.charts.presentation.components

import BottomNavigationBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.components.AppTopBar

@Composable
fun ChartsPage(
    navController: NavController
){
    MaterialTheme {
        Scaffold(
            topBar = {
                AppTopBar(
                    title = "Charts",
                    showMenu = true,
                    showBackButton = false,
                    onBackClick = {},
                    menuItems = emptyList()
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            }

        ) { padding ->

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

            }
        }
    }
}