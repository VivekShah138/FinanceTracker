package com.example.financetracker.main_page_feature.home_page.presentation.components

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.text.format.Formatter
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonetizationOn
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
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale


@Composable
fun BudgetProgressBar(
    spentAmount: Float,
    totalBudget: Float,
    sliderAlert: Float,
    modifier: Modifier = Modifier,
    displayText: String
) {
    val rawProgress = if (totalBudget > 0) (spentAmount / totalBudget).coerceIn(0f, 1f) else 0f

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    )  {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = displayText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = String.format(Locale.US, "%.2f%%", (rawProgress * 100)),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            )

            Text(
                text = spentAmount.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        CustomLinearProgressIndicator(
            spentAmount = spentAmount,
            totalBudget = totalBudget,
            sliderAlert = sliderAlert,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BudgetProgressBarPreview() {
    MaterialTheme {
        BudgetProgressBar(
            spentAmount = 65f,
            totalBudget = 150f,
            sliderAlert = 80f,
            displayText = "Overall"
        )
    }
}

@Composable
fun CustomLinearProgressIndicator(
    spentAmount: Float,
    totalBudget: Float,
    sliderAlert: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFC8E6C9), // Green100
    clipShape: Shape = RoundedCornerShape(16.dp),
    height: Dp = 8.dp,
    animated: Boolean = true
) {
    val clampedProgress = if (totalBudget > 0) (spentAmount / totalBudget).coerceIn(0f, 1f) else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = clampedProgress,
        label = "customProgressAnimation"
    )



    fun interpolateHSV(startColor: Color, endColor: Color, fraction: Float): Color {
        val startHsv = FloatArray(3)
        val endHsv = FloatArray(3)

        android.graphics.Color.colorToHSV(startColor.toArgb(), startHsv)
        android.graphics.Color.colorToHSV(endColor.toArgb(), endHsv)

        val interpolatedHsv = FloatArray(3) { i ->
            if (i == 0) { // hue needs circular interpolation
                val diff = (endHsv[0] - startHsv[0] + 360) % 360
                val shortestAngle = if (diff > 180) diff - 360 else diff
                (startHsv[0] + shortestAngle * fraction + 360) % 360
            } else {
                startHsv[i] + (endHsv[i] - startHsv[i]) * fraction
            }
        }

        return Color(android.graphics.Color.HSVToColor(interpolatedHsv))
    }

    // Dynamic color logic
    val progressColor = when {
        clampedProgress >= 1f -> Color.Red
        clampedProgress >= 0.75f -> interpolateHSV(Color(0xFFFF9800), Color.Red, (clampedProgress - 0.75f) / 0.25f)
        clampedProgress >= 0.5f -> interpolateHSV(Color(0xFF388E3C), Color(0xFFFF9800), (clampedProgress - 0.5f) / 0.25f)
        else -> Color(0xFF388E3C)
    }




    Box(
        modifier = modifier
            .fillMaxWidth()
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
