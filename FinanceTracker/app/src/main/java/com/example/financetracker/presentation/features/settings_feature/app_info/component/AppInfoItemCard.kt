package com.example.financetracker.presentation.features.settings_feature.app_info.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.ui.theme.FinanceTrackerTheme

@Composable
fun AppInfoItemCard(
    leadingIcon: ImageVector,
    externalIcon: ImageVector? = null,
    headlineContent: String,
    supportingContent: String? = null,
    onClick: (() -> Unit)? = null,
    isChecked: Boolean = false,
    onCheck:((Boolean) -> Unit)? = null,
){
    ListItem(
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        headlineContent = {
            Text(
                text = headlineContent,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        },
        supportingContent = supportingContent?.let{
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        trailingContent = {
            if(externalIcon != null)
            {
                Icon(imageVector = externalIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            if(onCheck != null){
                Switch(
                    checked = isChecked,
                    onCheckedChange = { onCheck.invoke(it) },
                    modifier = Modifier.scale(0.8f)
                )
            }
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.clickable{
            onClick?.invoke()
        }
    )
}

@Preview(
    showBackground = true,
    name = "link"
)
@Composable
fun AppInfoItemCardPreviewLink(){
    FinanceTrackerTheme {
        AppInfoItemCard(
            leadingIcon = Icons.Default.Person,
            externalIcon = Icons.AutoMirrored.Filled.OpenInNew,
            headlineContent = "LinkedIn",
            onClick ={

            },
            supportingContent = "Android Developer"
        )
    }
}

@Preview(
    showBackground = true,
    name = "switch"
)
@Composable
fun AppInfoItemCardPreview(){
    FinanceTrackerTheme {
        AppInfoItemCard(
            leadingIcon = Icons.Default.Person,
            headlineContent = "LinkedIn",
            onCheck = {

            },
            isChecked = false,
            supportingContent = "Android Developer"
        )
    }
}