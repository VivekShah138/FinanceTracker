package com.example.financetracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.settings_feature.help_and_feedback.app_info.component.AppInfoScreen
import com.example.financetracker.presentation.features.settings_feature.help_and_feedback.core_component.HelpAndFeedbackScreen
import com.example.financetracker.presentation.features.settings_feature.help_and_feedback.feedback.component.FeedbackScreen
import com.example.financetracker.presentation.features.settings_feature.help_and_feedback.terms_and_privacy_policy.component.TermsAndPrivacyPolicyScreen

fun NavGraphBuilder.helpAndFeedbackGraph(
    navController: NavController,
){
    composable<Screens.HelpAndFeedbackScreen>{
        HelpAndFeedbackScreen(
            navController = navController
        )
    }

    composable<Screens.FeedbackScreen> {
        FeedbackScreen(navController = navController)
    }

    composable<Screens.AppInfoScreen> {
        AppInfoScreen(navController = navController)
    }

    composable<Screens.TermsAndPrivacyPolicyScreen> {
        TermsAndPrivacyPolicyScreen(navController = navController)
    }
}