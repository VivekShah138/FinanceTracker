package com.example.financetracker.budget_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.financetracker.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderWithValueInsideCustomThumb(
    sliderPosition: Float,
    onValueChange: (Float) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    // Custom slider with custom thumb
    Slider(
        value = sliderPosition,
        onValueChange =  onValueChange ,
        valueRange = 0f..100f,
        steps = 19,
        modifier = Modifier.fillMaxWidth(),
//        interactionSource = interactionSource,
        thumb = {
            CustomThumb(sliderPosition)
        },
//        colors = SliderColors(
//            activeTrackColor = MaterialTheme.colorScheme.primary,
//            inactiveTrackColor = MaterialTheme.colorScheme.primaryContainer,
//            thumbColor = MaterialTheme.colorScheme.primary
//        )
        colors = SliderColors(
            thumbColor =  MaterialTheme.colorScheme.primary,
            activeTrackColor = MaterialTheme.colorScheme.primary,
            inactiveTrackColor = MaterialTheme.colorScheme.surface,
//            activeTickColor = MaterialTheme.colorScheme.onPrimary,
            activeTickColor = Color.Transparent,
//            inactiveTickColor = MaterialTheme.colorScheme.onSurface,
            inactiveTickColor = Color.Transparent,
            disabledThumbColor = MaterialTheme.colorScheme.onSurface,
            disabledActiveTrackColor= MaterialTheme.colorScheme.onSurface,
            disabledInactiveTrackColor = MaterialTheme.colorScheme.onSurface,
            disabledActiveTickColor = MaterialTheme.colorScheme.onSurface,
            disabledInactiveTickColor = MaterialTheme.colorScheme.onSurface
    )

    )
}

// Custom Thumb with Text Inside
@Composable
fun CustomThumb(sliderPosition: Float) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(width = 40.dp, height = 30.dp) // Thumb size
            .clip(RoundedCornerShape(25.dp)) // Thumb shape
            .graphicsLayer {
                // Optionally, add scale or rotation effects here if needed
            }
            .background(MaterialTheme.colorScheme.primary) // Thumb color
    ) {
        Text(
            text = "${sliderPosition.toInt()}%",
            style = TextStyle(
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SliderWithValueInsideCustomThumbPreview(){

    AppTheme(dynamicColor = false) {

        SliderWithValueInsideCustomThumb(
            sliderPosition = 50f,
            onValueChange = {

            }
        )

    }

}



