package com.example.financetracker.setup_account.presentation.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.example.financetracker.setup_account.presentation.ProfileSetUpEvents

@Composable
fun MySwitch(text:String,
             isCheck: Boolean,
             onCheckChange: () -> Unit
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(IntrinsicSize.Min), // To align items vertically
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isCheck,
            onCheckedChange = {
                onCheckChange()
            },
            modifier = Modifier.scale(0.7f)
        )
    }
}


@Composable
fun SettingsSwitchItem(
    text: String,
    isCheck: Boolean,
    onCheckChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
//            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)  // Padding inside the row
                .height(30.dp),  // Align items vertically
            verticalAlignment = Alignment.CenterVertically  // Align items vertically in the center
        ) {
            Icon(
                imageVector = Icons.Default.Cloud,
                contentDescription = "Profile Icon",
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)  // Text takes the available space
            )
            Switch(
                checked = isCheck,
                onCheckedChange = { onCheckChange(it) },
                modifier = Modifier.scale(0.8f)  // Adjust switch size
            )
        }
    }
}
