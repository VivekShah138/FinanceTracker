package com.example.financetracker.main_page_feature.settings.presentation.components


import BottomNavigationBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.core.presentation.components.AppTopBar
import com.example.financetracker.setup_account.presentation.components.MySwitch
import com.example.financetracker.setup_account.presentation.components.SettingsSwitchItem

@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun SettingsPagePreview(){
    MaterialTheme {
        Scaffold(
            topBar = {
                AppTopBar(
                    title = "Settings",
                    showMenu = true,
                    showBackButton = false,
                    onBackClick = {},
                    menuItems = emptyList()
                )
            },
            bottomBar = {
                BottomNavigationBar()
            }

        ) { padding ->

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {  },
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(30.dp),  // To align items vertically
                        verticalAlignment = Alignment.CenterVertically  // Align items vertically in the center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Icon",
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        Text(
                            text = "Profile",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)  // This ensures the text takes available space
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Navigate to Profile"
                        )
                    }
                }

                SettingsSwitchItem(
                    text = "Cloud Sync",
                    isCheck = false,
                    onCheckChange = {}
                )
            }
        }
    }
}