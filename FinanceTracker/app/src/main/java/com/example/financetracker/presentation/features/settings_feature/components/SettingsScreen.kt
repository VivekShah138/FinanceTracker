package com.example.financetracker.presentation.features.settings_feature.components



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.presentation.core_components.AppTopBar
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.settings_feature.SettingEvents
import com.example.financetracker.presentation.features.settings_feature.SettingStates
import com.example.financetracker.presentation.features.settings_feature.SettingsViewModel
import com.example.financetracker.presentation.features.setup_account_feature.components.SettingsSwitchItem
import com.example.financetracker.ui.theme.FinanceTrackerTheme


@Composable
fun SettingsRoot(
    navController: NavController,
    viewModel: SettingsViewModel
){
    val states by viewModel.settingStates.collectAsStateWithLifecycle()

    SettingsScreen(
        navController =navController,
        states = states,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun SettingsScreen(
    navController: NavController,
    states: SettingStates,
    onEvent: (SettingEvents) -> Unit
){
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

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Surface(
                modifier = Modifier.fillMaxSize()
                    .padding(vertical = 8.dp)
                    .padding(horizontal = 16.dp),
                tonalElevation = 2.dp,
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 4.dp,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {

                Column(
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    NameCard(states.name)

                    SettingsItemCard(
                        leadingImageVector = Icons.Default.Person,
                        leadingImageVectorState = true,
                        trailingImageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        trailingImageVectorState = true,
                        onClick = {
                            navController.navigate(route = Screens.ProfileSetUpScreen)
                        },
                        text = "Profile"
                    )

                    SettingsSwitchItem(
                        text = "Cloud Sync",
                        isCheck = states.cloudSync,
                        onCheckChange = {
                            onEvent(SettingEvents.ChangeCloudSync(it))
                        }
                    )

                    SettingsItemCard(
                        leadingImageVector = Icons.Default.Category,
                        leadingImageVectorState = true,
                        trailingImageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        trailingImageVectorState = true,
                        onClick = {
                            navController.navigate(route = Screens.CategoriesScreen)
                        },
                        text = "Categories"
                    )

                    SettingsItemCard(
                        leadingImageVector = Icons.Default.MonetizationOn,
                        leadingImageVectorState = true,
                        trailingImageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        trailingImageVectorState = true,
                        onClick = {
                            navController.navigate(route = Screens.BudgetScreen)
                        },
                        text = "Budget",
                        showBadge = !states.budgetExist
                    )

                    SettingsSwitchItem(
                        text = "Dark Mode",
                        isCheck = states.darkMode,
                        onCheckChange = {
                            onEvent(SettingEvents.ChangeDarkMode(it))
                        }
                    )

                    SettingsItemCard(
                        leadingImageVector = Icons.AutoMirrored.Filled.Help,
                        leadingImageVectorState = true,
                        trailingImageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        trailingImageVectorState = true,
                        onClick = {
                            navController.navigate(Screens.HelpAndFeedbackScreen)
                        },
                        text = "Help and Feedback"
                    )


                    SettingsItemCard(
                        leadingImageVector = Icons.AutoMirrored.Filled.Logout,
                        leadingImageVectorState = true,
                        trailingImageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        trailingImageVectorState = true,
                        onClick = {
                            onEvent(SettingEvents.LogOut)
                            navController.navigate(route = Screens.StartUpPageScreen)
                        },
                        text = "Log Out"
                    )
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun SettingsPagePreview(){

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