package com.example.financetracker.categories_feature.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.main_page_feature.finance_entry.finance_entry_core.presentation.components.getCategoryIcon


@Composable
fun SingleCategoryDisplay(
    onClickDelete: () -> Unit,
    onClickItem: () -> Unit,
    text: String,
    isPredefined: Boolean
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClickItem()
            },
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){

            Icon(
                imageVector = getCategoryIcon(text),
                contentDescription = null,
                modifier = Modifier.size(15.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = text,
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )
            if(!isPredefined){
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onClickDelete()
                        }
                )
            }
        }
    }
}

@Composable
fun SingleCategoryDisplay2(
    onClickDelete: () -> Unit,
    onClickItem: () -> Unit,
    text: String,
    isPredefined: Boolean
) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clickable {
                    onClickItem()
                },
//                .background(color =  MaterialTheme.colorScheme.primaryContainer),
            verticalAlignment = Alignment.CenterVertically
        ){


            Card(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                border = BorderStroke(width = 0.5.dp,color =  MaterialTheme.colorScheme.onSurface),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getCategoryIcon(text),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = text,
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )
            if(!isPredefined){
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onClickDelete()
                        }
                )
            }
        }
}

@Preview(
    showBackground = true,
//    showSystemUi = true
)
@Composable
fun SingleCategoryDisplayPreview(){

    SingleCategoryDisplay2(
        onClickDelete = {},
        onClickItem = {},
        text = "HouseHold",
        isPredefined = true
    )
}