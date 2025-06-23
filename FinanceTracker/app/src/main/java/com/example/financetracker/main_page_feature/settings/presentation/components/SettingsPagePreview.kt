package com.example.financetracker.main_page_feature.settings.presentation.components


import BottomNavigationBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.main_page_feature.finance_entry.finance_entry_core.presentation.components.getCategoryIcon
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
//            bottomBar = {
//                BottomNavigationBar()
//            }

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

                        NameCard("Vivek Shah")


                        SettingsItemCard(
                            leadingImageVector = Icons.Default.Person,
                            leadingImageVectorState = true,
                            trailingImageVector = Icons.Default.ArrowForwardIos,
                            trailingImageVectorState = true,
                            onClick = {},
                            text = "Profile"
                        )

                        SettingsSwitchItem(
                            text = "Cloud Sync",
                            isCheck = false,
                            onCheckChange = {}
                        )

                        SettingsItemCard(
                            leadingImageVector = Icons.Default.Category,
                            leadingImageVectorState = true,
                            trailingImageVector = Icons.Default.ArrowForwardIos,
                            trailingImageVectorState = true,
                            onClick = {},
                            text = "Categories"
                        )

                        SettingsItemCard(
                            leadingImageVector = Icons.Default.MonetizationOn,
                            leadingImageVectorState = true,
                            trailingImageVector = Icons.Default.ArrowForwardIos,
                            trailingImageVectorState = true,
                            onClick = {},
                            text = "Budget",
                            showBadge = true
                        )

                        SettingsSwitchItem(
                            text = "Dark Mode",
                            isCheck = false,
                            onCheckChange = {}
                        )


                        SettingsItemCard(
                            leadingImageVector = Icons.Default.Logout,
                            leadingImageVectorState = true,
                            trailingImageVector = Icons.Default.ArrowForwardIos,
                            trailingImageVectorState = true,
                            onClick = {},
                            text = "Log Out"
                        )



                    }




                }



                SettingsItemCard(
                    leadingImageVector = Icons.Default.Person,
                    leadingImageVectorState = true,
                    trailingImageVector = Icons.Default.ArrowForward,
                    trailingImageVectorState = true,
                    onClick = {},
                    text = "Profile"
                )

                SettingsSwitchItem(
                    text = "Cloud Sync",
                    isCheck = false,
                    onCheckChange = {}
                )

                SettingsItemCard(
                    leadingImageVector = Icons.Default.Category,
                    leadingImageVectorState = true,
                    trailingImageVector = Icons.Default.ArrowForward,
                    trailingImageVectorState = true,
                    onClick = {},
                    text = "Categories"
                )

                SettingsItemCard(
                    leadingImageVector = Icons.Default.MonetizationOn,
                    leadingImageVectorState = true,
                    trailingImageVector = Icons.Default.ArrowForward,
                    trailingImageVectorState = true,
                    onClick = {},
                    text = "Budget"
                )
            }
        }
    }
}

@Composable
fun NameCard(
    name: String
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){

            Card(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = name,
                fontSize = 25.sp,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

        }

        Spacer(modifier = Modifier.width(16.dp))

        HorizontalDivider(color = MaterialTheme.colorScheme.inverseSurface)

    }
}