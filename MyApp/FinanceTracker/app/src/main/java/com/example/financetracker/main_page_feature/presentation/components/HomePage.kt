package com.example.financetracker.main_page_feature.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.financetracker.core.presentation.utils.Screens
import com.example.financetracker.main_page_feature.presentation.HomePageEvents
import com.example.financetracker.main_page_feature.presentation.HomePageViewModel



@Composable
fun HomePageScreen(
    viewModel: HomePageViewModel,
    navController: NavController
){
    MaterialTheme {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "HomePage",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick ={
                    viewModel.onEvent(HomePageEvents.Logout)
                    navController.navigate(route = Screens.LogInScreen.routes )
                }
            ) {
                Text("Logout")
            }

            Button(
                onClick ={
                    navController.navigate(route = Screens.SetUpAccountPageScreen.routes)
                }
            ) {
                Text("SetUpAccount")
            }
        }
    }
}