package com.example.financetracker.core.core_presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.core.core_presentation.utils.MenuItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    showBackButton: Boolean = false,  // Optional Back Button
    showMenu: Boolean = false,        // Optional Menu Button
    onBackClick: () -> Unit,     // Non-nullable function
    menuItems: List<MenuItems> = listOf(),      // Non-nullable function
    modifier: Modifier = Modifier,
    customActions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    titleContentColor: Color = MaterialTheme.colorScheme.onBackground,
    actionIconContentColor: Color = MaterialTheme.colorScheme.onBackground,
    navigationIconContentColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            if (showBackButton) Text(text = title, style = TextStyle(fontSize = 20.sp), textAlign = TextAlign.Center,modifier = Modifier.padding(vertical = 5.dp))
            else Text(text = title, style = TextStyle(fontSize = 20.sp), modifier = Modifier.padding(start = 20.dp).padding(vertical = 5.dp))
        },
        navigationIcon = {
            if(showBackButton){
                IconButton(onClick = { onBackClick() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = {
            if (showMenu && menuItems.isNotEmpty()) {
                var menuExpanded by remember { mutableStateOf(false) }

                IconButton(onClick = { menuExpanded = !menuExpanded }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    menuItems.forEach{ menuItem ->
                        DropdownMenuItem(
                            text = { Text(menuItem.text) },
                            onClick = {
                                menuExpanded = false
                                menuItem.onClick()  // Directly invoke onMenuClick()
                            }
                        )
                    }
                }
            }
            else(
                customActions()
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = titleContentColor,
            navigationIconContentColor = navigationIconContentColor,
            actionIconContentColor = actionIconContentColor
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePagePreviewScreen() {
    AppTopBar(
        title = "Title",
        showBackButton = true,
        showMenu = false,
        onBackClick = {},
        menuItems = emptyList<MenuItems>(),
        customActions = {
            IconButton(onClick = { /* handle delete */ }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    )
}
