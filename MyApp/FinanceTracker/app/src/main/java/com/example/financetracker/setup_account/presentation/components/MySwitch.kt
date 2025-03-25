package com.example.financetracker.setup_account.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.example.financetracker.setup_account.presentation.ProfileSetUpEvents

@Composable
fun MySwitch(text:String,
             isCheck: Boolean,
             onEvent:(ProfileSetUpEvents) -> Unit
){

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isCheck,
            onCheckedChange = {
                onEvent(ProfileSetUpEvents.changeCloudSync(it))
            },
            modifier = Modifier.scale(0.7f)
        )
    }
}