package com.example.financetracker.presentation.core_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.navigation.BottomNavItem
import com.example.financetracker.navigation.BottomNavItemsList
import com.example.financetracker.navigation.Screens

@Composable
fun BottomNavigationBar(
    navController: NavController = rememberNavController(),
    items: List<BottomNavItem> = BottomNavItemsList,
    showBadge: Boolean = false,
    currentRoute: NavDestination?,
    containerColor: Color = MaterialTheme.colorScheme.background
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        NavigationBar(
            containerColor = containerColor,
            modifier = Modifier.height(100.dp)
        ) {
            items.forEachIndexed { index, bottomNavItem ->
                val ifIncluded = currentRoute?.hierarchy?.any {
                    if(bottomNavItem.screen == Screens.ViewRecordsScreen){
                        it.route?.startsWith(bottomNavItem.screen.routes) == true
                    }else{
                        it.route == bottomNavItem.screen.routes
                    }

                }

                NavigationBarItem(
                    selected = ifIncluded == true,
                    onClick = {
                        val route = if (bottomNavItem.screen.routes == Screens.ViewRecordsScreen.routes) {
                            "${bottomNavItem.screen.routes}/0"
                        } else {
                            bottomNavItem.screen.routes
                        }
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (bottomNavItem.screen is Screens.SettingScreen && showBadge) {
                                    Badge(
                                        modifier = Modifier.offset(x = (-8.5).dp,y = (24).dp)
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = bottomNavItem.icon,
                                contentDescription = bottomNavItem.label,
                                modifier = Modifier.size(
                                    if (bottomNavItem.screen is Screens.AddTransactionsScreen) 36.dp else 24.dp
                                )
                            )
                        }

                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    }
}
