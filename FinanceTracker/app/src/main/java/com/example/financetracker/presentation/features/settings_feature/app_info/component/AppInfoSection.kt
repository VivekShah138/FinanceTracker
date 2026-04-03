package com.example.financetracker.presentation.features.settings_feature.app_info.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.ui.theme.FinanceTrackerTheme

@Composable
fun AppInfoSection(
    title: String? = null,
    itemCardList: List<AppInfoItem>
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if(title != null){
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))

                itemCardList.forEach { item->
                    AppInfoItemCard(
                        leadingIcon = item.leadingIcon,
                        headlineContent = item.headlineContent,
                        supportingContent = item.supportingContent,
                        externalIcon = item.externalIcon,
                        onClick = item.onClick,
                        isChecked = item.isChecked,
                        onCheck = item.onCheck
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AppInfoSectionPreview(){
    FinanceTrackerTheme(darkTheme = true) {
        AppInfoSection(
            title = "DEVELOPER",
            itemCardList = listOf(
                AppInfoItem(
                    leadingIcon = Icons.Default.Person,
                    headlineContent = "Developed by Vivek Shah",
                    supportingContent = "Android Developer",
                ),
                AppInfoItem(
                    leadingIcon = Icons.Default.Link,
                    headlineContent = "LinkedIn",
                    externalIcon = Icons.AutoMirrored.Filled.OpenInNew
                ),
                AppInfoItem(
                    leadingIcon = Icons.Default.Code,
                    headlineContent = "GitHub",
                    externalIcon = Icons.AutoMirrored.Filled.OpenInNew
                )
            )
        )
    }
}