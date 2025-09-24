package com.example.financetracker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

class FinanceTrackerTopLevelDestination(
    private val navController: NavController
){
    fun navigateTo(destination: BottomNavItem) {
        navController.navigate(destination.screen) {

            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destination on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true

            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}


data class BottomNavItem (
    val screen: Screens,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String
)

val BottomNavItemsList = listOf(
    BottomNavItem(
        Screens.HomePageScreen,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        label = "Home"
    ),
    BottomNavItem(
        Screens.ViewRecordsScreen(tabIndex = 0),
        selectedIcon = Icons.AutoMirrored.Filled.List,
        unselectedIcon = Icons.AutoMirrored.Outlined.List,
        label = "Records"
    ),
    BottomNavItem(
        Screens.AddTransactionsScreen,
        selectedIcon = Icons.Filled.AddCircle,
        unselectedIcon = Icons.Outlined.AddCircle,
        label = "Add"
    ),
    BottomNavItem(
        Screens.ChartsScreen,
        selectedIcon = Icons.Filled.AutoGraph,
        unselectedIcon = Icons.Outlined.AutoGraph,
        label = "Charts"
    ),
    BottomNavItem(
        Screens.SettingScreen,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        label = "Settings"
    )
)