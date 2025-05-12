package com.example.financetracker.budget_feature.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ReceiveAlertSwitch(
    text:String,
    isCheck: Boolean,
    onCheckChange: (Boolean) -> Unit,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = 16.sp,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    ){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(17.dp)
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, textAlign = textAlign,fontSize = fontSize, color = textColor,modifier = Modifier.weight(1f))
        Switch(
            checked = isCheck,
            onCheckedChange = {
                onCheckChange(it)
            },
            modifier = Modifier.scale(0.8f)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ReceiveAlertSwitchPreview(){

    ReceiveAlertSwitch(
        text = "Receive Alert",
        isCheck = false,
        onCheckChange = {

        }
    )

}