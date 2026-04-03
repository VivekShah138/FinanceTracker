package com.example.financetracker.presentation.features.settings_feature.core_settings.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.presentation.core_components.AppTopBar
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.settings_feature.app_info.component.AppInfoItem
import com.example.financetracker.presentation.features.settings_feature.app_info.component.AppInfoItemCard
import com.example.financetracker.presentation.features.settings_feature.app_info.component.AppInfoSection
import com.example.financetracker.presentation.features.settings_feature.core_settings.SettingEvents
import com.example.financetracker.presentation.features.settings_feature.core_settings.SettingStates
import com.example.financetracker.presentation.features.settings_feature.core_settings.SettingsViewModel
import com.example.financetracker.presentation.features.setup_account_feature.components.SettingsSwitchItem
import com.example.financetracker.ui.theme.FinanceTrackerTheme


@Composable
fun SettingsRoot(
    navController: NavController,
    viewModel: SettingsViewModel
) {
    val states by viewModel.settingStates.collectAsStateWithLifecycle()

    SettingsScreen(
        navController = navController,
        states = states,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun SettingsScreen(
    navController: NavController,
    states: SettingStates,
    onEvent: (SettingEvents) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Settings",
                showMenu = true,
                showBackButton = false,
                onBackClick = {},
                menuItems = emptyList()
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppInfoSection(
                itemCardList = listOf(
                    AppInfoItem(
                        leadingIcon = Icons.Default.Person,
                        externalIcon = Icons.Default.ChevronRight,
                        headlineContent = "Profile",
                        onClick = {
                            navController.navigate(route = Screens.ProfileSetUpScreen)
                        }
                    ),
                    AppInfoItem(
                        leadingIcon = Icons.Default.Cloud,
                        headlineContent = "Cloud Sync",
                        isChecked = states.cloudSync,
                        onCheck = {
                            onEvent(SettingEvents.ChangeCloudSync(it))
                        }
                    ),
                    AppInfoItem(
                        leadingIcon = Icons.Default.Category,
                        externalIcon = Icons.Default.ChevronRight,
                        headlineContent = "Categories",
                        onClick = {
                            navController.navigate(route = Screens.CategoriesScreen)
                        }
                    ),
                    AppInfoItem(
                        leadingIcon = Icons.Default.MonetizationOn,
                        externalIcon = Icons.Default.ChevronRight,
                        headlineContent = "Budget",
                        onClick = {
                            navController.navigate(route = Screens.BudgetScreen)
                        }
                    ),
                    AppInfoItem(
                        leadingIcon = Icons.Default.Brightness6,
                        headlineContent = "Dark Mode",
                        isChecked = states.darkMode,
                        onCheck = {
                            onEvent(SettingEvents.ChangeDarkMode(it))
                        }
                    ),
                    AppInfoItem(
                        leadingIcon = Icons.Default.Info,
                        externalIcon = Icons.Default.ChevronRight,
                        headlineContent = "App Info",
                        onClick = {
                            navController.navigate(Screens.AppInfoScreen)
                        }
                    ),
                    AppInfoItem(
                        leadingIcon = Icons.AutoMirrored.Filled.Logout,
                        externalIcon = Icons.Default.ChevronRight,
                        headlineContent = "Log Out",
                        onClick = {
                            onEvent(SettingEvents.LogOut)
                            navController.navigate(route = Screens.StartUpPageScreen)
                        }
                    )
                )
            )
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SettingsPagePreview() {

    FinanceTrackerTheme {
        SettingsScreen(
            navController = rememberNavController(),
            states = SettingStates(

            ),
            onEvent = {

            }
        )
    }
}