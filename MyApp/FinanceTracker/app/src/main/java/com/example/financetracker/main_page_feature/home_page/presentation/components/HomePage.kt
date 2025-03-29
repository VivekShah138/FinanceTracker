package com.example.financetracker.main_page_feature.home_page.presentation.components

import BottomNavigationBar
import android.view.MenuItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.financetracker.core.presentation.MenuItems
import com.example.financetracker.core.presentation.components.AppTopBar
import com.example.financetracker.core.presentation.utils.Screens
import com.example.financetracker.main_page_feature.home_page.presentation.HomePageEvents
import com.example.financetracker.main_page_feature.home_page.presentation.HomePageViewModel



@Composable
fun HomePageScreen(
    viewModel: HomePageViewModel,
    navController: NavController
){



    MaterialTheme {

        Scaffold(
            topBar = {
                AppTopBar(
                    title = "Home Page",
                    showMenu = true,
                    showBackButton = false,
                    onBackClick = {},
                    menuItems = listOf<MenuItems>(
                        MenuItems(
                            text = "Setting",
                            onClick = {}
                        ),
                        MenuItems(
                            text = "Logout",
                            onClick = {
                                viewModel.onEvent(HomePageEvents.Logout)
                                navController.navigate(route = Screens.LogInScreen.routes )
                            }
                        )
                    )
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) {InnerPadding ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(InnerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}