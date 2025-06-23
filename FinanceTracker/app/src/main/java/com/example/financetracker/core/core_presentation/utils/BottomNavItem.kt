package com.example.financetracker.core.core_presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem (
    val screen: Screens,
    val icon: ImageVector,
    val label: String
)

val BottomNavItemsList = listOf(
    BottomNavItem(Screens.HomePageScreen, icon = Icons.Default.Home, label = "Home"),
    BottomNavItem(Screens.ViewRecordsScreen, icon = Icons.AutoMirrored.Filled.List, label = "View Records"),
    BottomNavItem(Screens.AddTransactionsScreen, icon = Icons.Default.AddCircle, label = "Add"),
    BottomNavItem(Screens.GraphicalVisualizationScreen, icon = Icons.Default.AutoGraph, label = "Charts"),
    BottomNavItem(Screens.SettingScreen, icon = Icons.Default.Settings, label = "Settings")

)