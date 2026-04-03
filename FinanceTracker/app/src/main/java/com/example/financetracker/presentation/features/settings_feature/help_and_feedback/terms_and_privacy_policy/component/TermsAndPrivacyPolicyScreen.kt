package com.example.financetracker.presentation.features.settings_feature.help_and_feedback.terms_and_privacy_policy.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.ui.theme.FinanceTrackerTheme

@Composable
fun TermsAndPrivacyPolicyScreen(
    navController: NavController
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Terms and Privacy Policy")
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun TermsAndPrivacyPolicyScreenPreview(){
    FinanceTrackerTheme {
        TermsAndPrivacyPolicyScreen(
            navController = rememberNavController()
        )
    }
}