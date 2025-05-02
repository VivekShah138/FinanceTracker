package com.example.financetracker.main_page_feature.home_page.presentation.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BudgetProgressBar(
    spentAmount: Float,
    totalBudget: Float,
    modifier: Modifier = Modifier
) {
    val rawProgress = if (totalBudget > 0) (spentAmount / totalBudget).coerceIn(0f, 1f) else 0f
    val animatedProgress by animateFloatAsState(targetValue = rawProgress, label = "budgetProgress")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "${(rawProgress * 100).toInt()}% of budget used",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

//        LinearProgressIndicator(
//            progress = { animatedProgress }, // ✅ updated to lambda
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(12.dp),
//            color = MaterialTheme.colorScheme.primary,
//            trackColor = MaterialTheme.colorScheme.surfaceVariant
//        )
        CustomLinearProgressIndicator(
            spentAmount = spentAmount,
            totalBudget = totalBudget
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BudgetProgressBarPreview() {
    MaterialTheme {
        BudgetProgressBar(spentAmount = 350f, totalBudget = 1000f)

    }
}


@Composable
fun CustomLinearProgressIndicator(
    spentAmount: Float,
    totalBudget: Float,
    modifier: Modifier = Modifier,
    progressColor: Color = Color(0xFF388E3C),  // Default Green700
    backgroundColor: Color = Color(0xFFC8E6C9), // Default Green100
    clipShape: Shape = RoundedCornerShape(16.dp),
    height: Dp = 8.dp,
    animated: Boolean = true
) {
    val clampedProgress = if (totalBudget > 0) (spentAmount / totalBudget).coerceIn(0f, 1f) else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = clampedProgress,
        label = "customProgressAnimation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth() // ✅ add this!
            .clip(clipShape)
            .background(color = backgroundColor)
            .height(height)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(if (animated) animatedProgress else clampedProgress)
                .background(progressColor)
        )
    }
}
