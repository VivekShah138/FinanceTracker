package com.example.financetracker.presentation.features.help_and_feedback_feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.core_components.AppTopBar
import com.example.financetracker.presentation.features.settings_feature.SettingEvents
import com.example.financetracker.presentation.features.settings_feature.components.SettingsItemCard
import com.example.financetracker.ui.theme.FinanceTrackerTheme

@Composable
fun HelpAndFeedbackScreen(
    navController: NavController
){

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Help and Feedback",
                showBackButton = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SettingsItemCard(
                leadingImageVector = Icons.AutoMirrored.Filled.Help,
                leadingImageVectorState = true,
                trailingImageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                trailingImageVectorState = true,
                onClick = {
                    navController.navigate(Screens.FeedbackScreen)

                },
                text = "Help and Feedback"
            )

            SettingsItemCard(
                leadingImageVector = Icons.Default.Description,
                leadingImageVectorState = true,
                trailingImageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                trailingImageVectorState = true,
                onClick = {
                    navController.navigate(Screens.TermsAndPrivacyPolicyScreen)

                },
                text = "Terms and Privacy Policies"
            )

            SettingsItemCard(
                leadingImageVector = Icons.Default.Info,
                leadingImageVectorState = true,
                trailingImageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                trailingImageVectorState = true,
                onClick = {
                    navController.navigate(Screens.AppInfoScreen)
                },
                text = "App Info"
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HelpAndFeedbackScreenPreview(){
    FinanceTrackerTheme {
        HelpAndFeedbackScreen(
            navController = rememberNavController()
        )
    }
}