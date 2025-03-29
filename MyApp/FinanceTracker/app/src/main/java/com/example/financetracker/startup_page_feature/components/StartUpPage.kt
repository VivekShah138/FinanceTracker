package com.example.financetracker.startup_page_feature.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.startup_page_feature.StartPageViewModel
import com.example.financetracker.core.core_presentation.utils.Screens



@Composable
fun StartUpPageScreen(
    viewModel: StartPageViewModel,
    navController: NavController
){

    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()

    LaunchedEffect(isLoggedIn) {
        if(isLoggedIn){
            navController.navigate(route = Screens.HomePageScreen.routes) {
                popUpTo(route = Screens.LogInScreen.routes) {
                    inclusive = true
                }
            }
        }

    }

    MaterialTheme {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Welcome",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick ={
                    navController.navigate(route = Screens.RegistrationScreen.routes)
                }
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    navController.navigate(route = Screens.LogInScreen.routes)
                }
            ) {
                Text("Login")
            }
        }
    }
}