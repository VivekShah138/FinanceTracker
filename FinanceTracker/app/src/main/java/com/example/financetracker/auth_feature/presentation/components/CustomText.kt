package com.example.financetracker.auth_feature.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    text : String,
    size : TextUnit,
    fontStyle : FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Bold,
    color : Color = MaterialTheme.colorScheme.onBackground,
    heightValue : Dp = 80.dp,
    textAlign: TextAlign = TextAlign.Center
){
    Box(modifier = modifier){
        Text(
            modifier = Modifier
                .fillMaxWidth(),
//                .heightIn(heightValue),
            text = text,
            style = TextStyle(
                color = color,
                fontSize = size,
                fontWeight = fontWeight,
                fontStyle = fontStyle,
                textAlign = textAlign
            )
        )
    }
}