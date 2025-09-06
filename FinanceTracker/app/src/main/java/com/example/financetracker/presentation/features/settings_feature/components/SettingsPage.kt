package com.example.financetracker.presentation.features.settings_feature.components



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.presentation.core_components.AppTopBar
import com.example.financetracker.navigation.Screens
import com.example.financetracker.presentation.features.settings_feature.SettingEvents
import com.example.financetracker.presentation.features.settings_feature.SettingsViewModel
import com.example.financetracker.presentation.features.setup_account_feature.components.SettingsSwitchItem

@Composable
fun SettingsPage(
    navController: NavController,
    viewModel: SettingsViewModel
){

    val states by viewModel.settingStates.collectAsStateWithLifecycle()


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
//                            navController.navigate(route = Screens.ProfileSetUpScreen.routes)
                            navController.navigate(route = Screens.ProfileSetUpScreen)
                        },
                        text = "Profile"
                    )

                    SettingsSwitchItem(
                        text = "Cloud Sync",
                        isCheck = states.cloudSync,
                        onCheckChange = {
                            viewModel.onEvent(SettingEvents.ChangeCloudSync(it))
                        }
                    )

                    SettingsItemCard(
                        leadingImageVector = Icons.Default.Category,
                        leadingImageVectorState = true,
                        trailingImageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        trailingImageVectorState = true,
                        onClick = {
//                            navController.navigate(route = Screens.CategoriesScreen.routes)
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
//                            navController.navigate(route = Screens.BudgetScreen.routes)
                            navController.navigate(route = Screens.BudgetScreen)
                        },
                        text = "Budget",
                        showBadge = !states.budgetExist
                    )

                    SettingsSwitchItem(
                        text = "Dark Mode",
                        isCheck = states.darkMode,
                        onCheckChange = {
                            viewModel.onEvent(SettingEvents.ChangeDarkMode(it))
                        }
                    )


                    SettingsItemCard(
                        leadingImageVector = Icons.AutoMirrored.Filled.Logout,
                        leadingImageVectorState = true,
                        trailingImageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        trailingImageVectorState = true,
                        onClick = {
                            viewModel.onEvent(SettingEvents.LogOut)
//                            navController.navigate(route = Screens.StartUpPageScreen.routes )
                            navController.navigate(route = Screens.StartUpPageScreen)
                        },
                        text = "Log Out"
                    )
                }
            }
        }
    }
}