package com.example.financetracker.core.core_presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.R
import com.example.financetracker.ui.theme.DEFAULT_PADDING


@Composable
fun BottomNavigationBarPreview(
    items: List<String>,
    showBadge: Boolean = false
) {
    var selectedIndex by remember { mutableStateOf(0) } // To track selected item

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface, // Background color
        contentColor = MaterialTheme.colorScheme.onSurface, // Icon color
        tonalElevation = 8.dp,
        actions = {
            // Use Row to evenly space out the icons
            Row(
                modifier = Modifier.fillMaxWidth(), // Fill the width of the bottom bar
                horizontalArrangement = Arrangement.SpaceEvenly // Evenly space the icons
            ) {
                items.forEachIndexed { index, screen ->
                    // Update the selectedIndex when an icon is clicked
                    IconButton(
                        onClick = { selectedIndex = index }
                    ) {
                        BadgedBox(
                            badge = {
                                if (screen == "Setting" && showBadge) {
                                    Badge(
                                        modifier = Modifier.offset(x = (-8.5).dp,y = (24).dp)
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = when (screen) {
                                    "Home" -> Icons.Default.Home
                                    "Transactions" -> Icons.Default.List
                                    "Graphs" -> Icons.Default.AutoGraph
                                    "Add" -> Icons.Default.Add
                                    "Setting" -> Icons.Default.Settings
                                    else -> Icons.Default.Info
                                },
                                contentDescription = screen,
                                // Apply different color or size based on selection
                                tint = if (selectedIndex == index) MaterialTheme.colorScheme.primary else Color.Gray,
                                modifier = Modifier
                                    .size(if (selectedIndex == index) 30.dp else 24.dp) // Increase size when selected
                            )
                        }

                    }
                }
            }
        },
        modifier = Modifier.height(56.dp) // Adjust height to accommodate the FAB
    )
}




@Composable
fun MainScreen4() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(bottom =1.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        CustomBottomNavigation2()
        Fab()
    }
}

@Composable
fun CustomBottomNavigation2() {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .paint(
                painter = painterResource(R.drawable.bottom_navigation),
                contentScale = ContentScale.FillWidth
            )
            .padding(horizontal = 1.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val icons = listOf(Icons.Filled.CalendarToday, Icons.Filled.Group, Icons.Filled.Person, Icons.Filled.Settings)

        icons.forEachIndexed { index, image ->
            if (index == 2) {
                // Middle two icons, slightly lifted up
                IconButton(
                    onClick = { /* Handle Navigation */ },
                    modifier = Modifier
                        .weight(1f)
                        .offset(x = 30.dp) // Adjust height to fit the curve
                ) {
                    Icon(imageVector = image, contentDescription = null, tint = Color.White)
                }
            } else if (index == 1) {
                // Skip this position and leave space for FAB
                IconButton(
                    onClick = { /* Handle Navigation */ },
                    modifier = Modifier
                        .weight(1f)
                        .offset(x = -30.dp) // Adjust height to fit the curve
                ) {
                    Icon(imageVector = image, contentDescription = null, tint = Color.White)
                }
            } else {
                // Normal placement for first and last icons
                IconButton(
                    onClick = { /* Handle Navigation */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = image, contentDescription = null, tint = Color.White)
                }
            }
        }
    }
}

@Composable
fun Fab(
    onClick: () -> Unit = {}
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(bottom = DEFAULT_PADDING.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        AnimatedFab(
            icon = Icons.Default.Add,
            modifier = Modifier,
            onClick = onClick,
            backgroundColor = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun AnimatedFab(
    modifier: Modifier,
    icon: ImageVector? = null,
    opacity: Float = 1f,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
        containerColor = backgroundColor,
        modifier = modifier.scale(1.25f),
        shape = CircleShape
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = Color.White.copy(alpha = opacity)
            )
        }
    }
}


//@Composable
//fun CustomBottomNavigationWithFAB() {
//    val navItems = listOf("Home", "Search", "Profile")
//    var selectedItem by remember { mutableStateOf(0) }
//
//    // Define a custom path for the moon shape
//    val moonShape = object : Shape {
//        override fun createOutline(
//            size: Size,
//            density: Density,
//            layoutDirection: LayoutDirection
//        ): Outline {
//            val path = Path().apply {
//                // Start from the left bottom corner
//                moveTo(0f, size.height)
//                // Draw the bottom edge
//                lineTo(size.width / 2 - 50, size.height)
//                // Draw the moon shape (half-circle in the center)
//                cubicTo(
//                    size.width / 2, size.height + 50,
//                    size.width / 2 + 50, size.height + 50,
//                    size.width, size.height
//                )
//                // Close the path back to the left
//                lineTo(size.width, 0f)
//                lineTo(0f, 0f)
//                close()
//            }
//            return Outline.Generic(path)
//        }
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        // Your main content goes here
//
//        // Bottom navigation with custom moon shape
//        BottomNavigation(
//            backgroundColor = Color.White,
//            elevation = 8.dp,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .clip(moonShape) // Apply custom moon shape
//                .background(Color.White)
//        ) {
//            navItems.forEachIndexed { index, label ->
//                BottomNavigationItem(
//                    icon = {
//                        Icon(Icons.Default.Home, contentDescription = null)
//                    },
//                    label = { Text(label) },
//                    selected = selectedItem == index,
//                    onClick = {
//                        selectedItem = index
//                    },
//                    selectedContentColor = Color.Blue,
//                    unselectedContentColor = Color.Gray
//                )
//            }
//        }
//
//        // FAB in the center of the moon shape
//        FloatingActionButton(
//            onClick = { /* Handle FAB click */ },
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .offset(y = (-32).dp), // Adjust FAB position inside the moon
//            contentColor = Color.White,
//            backgroundColor = Color.Blue
//        ) {
//            Icon(Icons.Default.Add, contentDescription = "Add")
//        }
//    }
//}


@Preview(showBackground = true)
@Composable
fun PreviewMainScreen2() {
//    MainScreen4()
    BottomNavigationBarPreview(
        items = listOf("Home","Transactions","Add","Graphs","Setting"),
        showBadge = true
    )
}

