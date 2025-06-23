package com.example.financetracker.main_page_feature.settings.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SettingsItemCard(
    leadingImageVector: ImageVector,
    leadingImageVectorState: Boolean,
    text: String,
    trailingImageVector: ImageVector,
    trailingImageVectorState: Boolean,
    onClick: () -> Unit,
    showBadge: Boolean = false
){

//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable {
//                onClick()
//            },
//        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
//        shape = MaterialTheme.shapes.medium
//    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp)
                .clickable {
                    onClick()
                },  // To align items vertically
            verticalAlignment = Alignment.CenterVertically  // Align items vertically in the center
        ) {
            if(leadingImageVectorState){
                Icon(
                    imageVector = leadingImageVector,
                    contentDescription = "Leading Icon",
                    modifier = Modifier.padding(end = 16.dp)
                )
            }

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)  // This ensures the text takes available space
            )

            if(trailingImageVectorState){
                BadgedBox(
                    badge = {
                        if (showBadge) {
                            Badge()
                        }
                    }
                ) {
                    Icon(
                        imageVector = trailingImageVector,
                        contentDescription = "Navigate to "
                    )
                }
            }

        }
//    }

}