package com.example.financetracker.presentation.features.settings_feature.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
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
            modifier = Modifier.weight(1f)
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
}