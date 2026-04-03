package com.example.financetracker.presentation.features.settings_feature.help_and_feedback.feedback.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.ui.theme.FinanceTrackerTheme

@Composable
fun FeedbackScreen(
    navController: NavController
){

}

@Preview
@Composable
fun FeedbackScreenPreview(){
    FinanceTrackerTheme {
        FeedbackScreen(
            navController = rememberNavController()
        )
    }
}