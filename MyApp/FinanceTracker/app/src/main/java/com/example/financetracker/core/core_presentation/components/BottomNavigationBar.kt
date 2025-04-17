import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.core.core_presentation.utils.Screens

@Composable
fun BottomNavigationBar(navController: NavController = rememberNavController()) {
    val items = listOf(Screens.HomePageScreen,Screens.ViewRecordsScreen,Screens.AddTransactionsScreen,Screens.GraphicalVisualizationScreen,Screens.SettingScreen)

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 8.dp,
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items.forEach { screen ->

                    IconButton(onClick = {
                        if(navController.currentDestination?.route != screen.routes){
                            navController.navigate(screen.routes) // Navigate using NavController
                        }
                    }) {
                        Icon(
                            imageVector = when (screen) {
                                is Screens.HomePageScreen -> Icons.Default.Home
                                is Screens.ViewRecordsScreen -> Icons.AutoMirrored.Filled.List
                                is Screens.AddTransactionsScreen -> Icons.Default.AddCircle
                                is Screens.GraphicalVisualizationScreen -> Icons.Default.AutoGraph
                                is Screens.SettingScreen -> Icons.Default.Settings
                                else -> throw IllegalStateException("Unhandled screen: $screen") // Will never reach here
                            },
                            contentDescription = screen.routes,
                            tint = if (navController.currentDestination?.route == screen.routes) MaterialTheme.colorScheme.primary else Color.Gray,
                            modifier = Modifier.size(
                                if (screen is Screens.AddTransactionsScreen) 36.dp else 24.dp // Make "Add" icon bigger
                            )
                        )
                    }
                }
            }
        },
        modifier = Modifier.height(100.dp)
    )
}
