package com.example.financetracker.ui.theme
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.financetracker.ui.theme.backgroundDark
import com.example.financetracker.ui.theme.backgroundDarkHighContrast
import com.example.financetracker.ui.theme.backgroundDarkMediumContrast
import com.example.financetracker.ui.theme.backgroundLight
import com.example.financetracker.ui.theme.backgroundLightHighContrast
import com.example.financetracker.ui.theme.backgroundLightMediumContrast
import com.example.financetracker.ui.theme.errorContainerDark
import com.example.financetracker.ui.theme.errorContainerDarkHighContrast
import com.example.financetracker.ui.theme.errorContainerDarkMediumContrast
import com.example.financetracker.ui.theme.errorContainerLight
import com.example.financetracker.ui.theme.errorContainerLightHighContrast
import com.example.financetracker.ui.theme.errorContainerLightMediumContrast
import com.example.financetracker.ui.theme.errorDark
import com.example.financetracker.ui.theme.errorDarkHighContrast
import com.example.financetracker.ui.theme.errorDarkMediumContrast
import com.example.financetracker.ui.theme.errorLight
import com.example.financetracker.ui.theme.errorLightHighContrast
import com.example.financetracker.ui.theme.errorLightMediumContrast
import com.example.financetracker.ui.theme.inverseOnSurfaceDark
import com.example.financetracker.ui.theme.inverseOnSurfaceDarkHighContrast
import com.example.financetracker.ui.theme.inverseOnSurfaceDarkMediumContrast
import com.example.financetracker.ui.theme.inverseOnSurfaceLight
import com.example.financetracker.ui.theme.inverseOnSurfaceLightHighContrast
import com.example.financetracker.ui.theme.inverseOnSurfaceLightMediumContrast
import com.example.financetracker.ui.theme.inversePrimaryDark
import com.example.financetracker.ui.theme.inversePrimaryDarkHighContrast
import com.example.financetracker.ui.theme.inversePrimaryDarkMediumContrast
import com.example.financetracker.ui.theme.inversePrimaryLight
import com.example.financetracker.ui.theme.inversePrimaryLightHighContrast
import com.example.financetracker.ui.theme.inversePrimaryLightMediumContrast
import com.example.financetracker.ui.theme.inverseSurfaceDark
import com.example.financetracker.ui.theme.inverseSurfaceDarkHighContrast
import com.example.financetracker.ui.theme.inverseSurfaceDarkMediumContrast
import com.example.financetracker.ui.theme.inverseSurfaceLight
import com.example.financetracker.ui.theme.inverseSurfaceLightHighContrast
import com.example.financetracker.ui.theme.inverseSurfaceLightMediumContrast
import com.example.financetracker.ui.theme.onBackgroundDark
import com.example.financetracker.ui.theme.onBackgroundDarkHighContrast
import com.example.financetracker.ui.theme.onBackgroundDarkMediumContrast
import com.example.financetracker.ui.theme.onBackgroundLight
import com.example.financetracker.ui.theme.onBackgroundLightHighContrast
import com.example.financetracker.ui.theme.onBackgroundLightMediumContrast
import com.example.financetracker.ui.theme.onErrorContainerDark
import com.example.financetracker.ui.theme.onErrorContainerDarkHighContrast
import com.example.financetracker.ui.theme.onErrorContainerDarkMediumContrast
import com.example.financetracker.ui.theme.onErrorContainerLight
import com.example.financetracker.ui.theme.onErrorContainerLightHighContrast
import com.example.financetracker.ui.theme.onErrorContainerLightMediumContrast
import com.example.financetracker.ui.theme.onErrorDark
import com.example.financetracker.ui.theme.onErrorDarkHighContrast
import com.example.financetracker.ui.theme.onErrorDarkMediumContrast
import com.example.financetracker.ui.theme.onErrorLight
import com.example.financetracker.ui.theme.onErrorLightHighContrast
import com.example.financetracker.ui.theme.onErrorLightMediumContrast
import com.example.financetracker.ui.theme.onPrimaryContainerDark
import com.example.financetracker.ui.theme.onPrimaryContainerDarkHighContrast
import com.example.financetracker.ui.theme.onPrimaryContainerDarkMediumContrast
import com.example.financetracker.ui.theme.onPrimaryContainerLight
import com.example.financetracker.ui.theme.onPrimaryContainerLightHighContrast
import com.example.financetracker.ui.theme.onPrimaryContainerLightMediumContrast
import com.example.financetracker.ui.theme.onPrimaryDark
import com.example.financetracker.ui.theme.onPrimaryDarkHighContrast
import com.example.financetracker.ui.theme.onPrimaryDarkMediumContrast
import com.example.financetracker.ui.theme.onPrimaryLight
import com.example.financetracker.ui.theme.onPrimaryLightHighContrast
import com.example.financetracker.ui.theme.onPrimaryLightMediumContrast
import com.example.financetracker.ui.theme.onSecondaryContainerDark
import com.example.financetracker.ui.theme.onSecondaryContainerDarkHighContrast
import com.example.financetracker.ui.theme.onSecondaryContainerDarkMediumContrast
import com.example.financetracker.ui.theme.onSecondaryContainerLight
import com.example.financetracker.ui.theme.onSecondaryContainerLightHighContrast
import com.example.financetracker.ui.theme.onSecondaryContainerLightMediumContrast
import com.example.financetracker.ui.theme.onSecondaryDark
import com.example.financetracker.ui.theme.onSecondaryDarkHighContrast
import com.example.financetracker.ui.theme.onSecondaryDarkMediumContrast
import com.example.financetracker.ui.theme.onSecondaryLight
import com.example.financetracker.ui.theme.onSecondaryLightHighContrast
import com.example.financetracker.ui.theme.onSecondaryLightMediumContrast
import com.example.financetracker.ui.theme.onSurfaceDark
import com.example.financetracker.ui.theme.onSurfaceDarkHighContrast
import com.example.financetracker.ui.theme.onSurfaceDarkMediumContrast
import com.example.financetracker.ui.theme.onSurfaceLight
import com.example.financetracker.ui.theme.onSurfaceLightHighContrast
import com.example.financetracker.ui.theme.onSurfaceLightMediumContrast
import com.example.financetracker.ui.theme.onSurfaceVariantDark
import com.example.financetracker.ui.theme.onSurfaceVariantDarkHighContrast
import com.example.financetracker.ui.theme.onSurfaceVariantDarkMediumContrast
import com.example.financetracker.ui.theme.onSurfaceVariantLight
import com.example.financetracker.ui.theme.onSurfaceVariantLightHighContrast
import com.example.financetracker.ui.theme.onSurfaceVariantLightMediumContrast
import com.example.financetracker.ui.theme.onTertiaryContainerDark
import com.example.financetracker.ui.theme.onTertiaryContainerDarkHighContrast
import com.example.financetracker.ui.theme.onTertiaryContainerDarkMediumContrast
import com.example.financetracker.ui.theme.onTertiaryContainerLight
import com.example.financetracker.ui.theme.onTertiaryContainerLightHighContrast
import com.example.financetracker.ui.theme.onTertiaryContainerLightMediumContrast
import com.example.financetracker.ui.theme.onTertiaryDark
import com.example.financetracker.ui.theme.onTertiaryDarkHighContrast
import com.example.financetracker.ui.theme.onTertiaryDarkMediumContrast
import com.example.financetracker.ui.theme.onTertiaryLight
import com.example.financetracker.ui.theme.onTertiaryLightHighContrast
import com.example.financetracker.ui.theme.onTertiaryLightMediumContrast
import com.example.financetracker.ui.theme.outlineDark
import com.example.financetracker.ui.theme.outlineDarkHighContrast
import com.example.financetracker.ui.theme.outlineDarkMediumContrast
import com.example.financetracker.ui.theme.outlineLight
import com.example.financetracker.ui.theme.outlineLightHighContrast
import com.example.financetracker.ui.theme.outlineLightMediumContrast
import com.example.financetracker.ui.theme.outlineVariantDark
import com.example.financetracker.ui.theme.outlineVariantDarkHighContrast
import com.example.financetracker.ui.theme.outlineVariantDarkMediumContrast
import com.example.financetracker.ui.theme.outlineVariantLight
import com.example.financetracker.ui.theme.outlineVariantLightHighContrast
import com.example.financetracker.ui.theme.outlineVariantLightMediumContrast
import com.example.financetracker.ui.theme.primaryContainerDark
import com.example.financetracker.ui.theme.primaryContainerDarkHighContrast
import com.example.financetracker.ui.theme.primaryContainerDarkMediumContrast
import com.example.financetracker.ui.theme.primaryContainerLight
import com.example.financetracker.ui.theme.primaryContainerLightHighContrast
import com.example.financetracker.ui.theme.primaryContainerLightMediumContrast
import com.example.financetracker.ui.theme.primaryDark
import com.example.financetracker.ui.theme.primaryDarkHighContrast
import com.example.financetracker.ui.theme.primaryDarkMediumContrast
import com.example.financetracker.ui.theme.primaryLight
import com.example.financetracker.ui.theme.primaryLightHighContrast
import com.example.financetracker.ui.theme.primaryLightMediumContrast
import com.example.financetracker.ui.theme.scrimDark
import com.example.financetracker.ui.theme.scrimDarkHighContrast
import com.example.financetracker.ui.theme.scrimDarkMediumContrast
import com.example.financetracker.ui.theme.scrimLight
import com.example.financetracker.ui.theme.scrimLightHighContrast
import com.example.financetracker.ui.theme.scrimLightMediumContrast
import com.example.financetracker.ui.theme.secondaryContainerDark
import com.example.financetracker.ui.theme.secondaryContainerDarkHighContrast
import com.example.financetracker.ui.theme.secondaryContainerDarkMediumContrast
import com.example.financetracker.ui.theme.secondaryContainerLight
import com.example.financetracker.ui.theme.secondaryContainerLightHighContrast
import com.example.financetracker.ui.theme.secondaryContainerLightMediumContrast
import com.example.financetracker.ui.theme.secondaryDark
import com.example.financetracker.ui.theme.secondaryDarkHighContrast
import com.example.financetracker.ui.theme.secondaryDarkMediumContrast
import com.example.financetracker.ui.theme.secondaryLight
import com.example.financetracker.ui.theme.secondaryLightHighContrast
import com.example.financetracker.ui.theme.secondaryLightMediumContrast
import com.example.financetracker.ui.theme.surfaceBrightDark
import com.example.financetracker.ui.theme.surfaceBrightDarkHighContrast
import com.example.financetracker.ui.theme.surfaceBrightDarkMediumContrast
import com.example.financetracker.ui.theme.surfaceBrightLight
import com.example.financetracker.ui.theme.surfaceBrightLightHighContrast
import com.example.financetracker.ui.theme.surfaceBrightLightMediumContrast
import com.example.financetracker.ui.theme.surfaceContainerDark
import com.example.financetracker.ui.theme.surfaceContainerDarkHighContrast
import com.example.financetracker.ui.theme.surfaceContainerDarkMediumContrast
import com.example.financetracker.ui.theme.surfaceContainerHighDark
import com.example.financetracker.ui.theme.surfaceContainerHighDarkHighContrast
import com.example.financetracker.ui.theme.surfaceContainerHighDarkMediumContrast
import com.example.financetracker.ui.theme.surfaceContainerHighLight
import com.example.financetracker.ui.theme.surfaceContainerHighLightHighContrast
import com.example.financetracker.ui.theme.surfaceContainerHighLightMediumContrast
import com.example.financetracker.ui.theme.surfaceContainerHighestDark
import com.example.financetracker.ui.theme.surfaceContainerHighestDarkHighContrast
import com.example.financetracker.ui.theme.surfaceContainerHighestDarkMediumContrast
import com.example.financetracker.ui.theme.surfaceContainerHighestLight
import com.example.financetracker.ui.theme.surfaceContainerHighestLightHighContrast
import com.example.financetracker.ui.theme.surfaceContainerHighestLightMediumContrast
import com.example.financetracker.ui.theme.surfaceContainerLight
import com.example.financetracker.ui.theme.surfaceContainerLightHighContrast
import com.example.financetracker.ui.theme.surfaceContainerLightMediumContrast
import com.example.financetracker.ui.theme.surfaceContainerLowDark
import com.example.financetracker.ui.theme.surfaceContainerLowDarkHighContrast
import com.example.financetracker.ui.theme.surfaceContainerLowDarkMediumContrast
import com.example.financetracker.ui.theme.surfaceContainerLowLight
import com.example.financetracker.ui.theme.surfaceContainerLowLightHighContrast
import com.example.financetracker.ui.theme.surfaceContainerLowLightMediumContrast
import com.example.financetracker.ui.theme.surfaceContainerLowestDark
import com.example.financetracker.ui.theme.surfaceContainerLowestDarkHighContrast
import com.example.financetracker.ui.theme.surfaceContainerLowestDarkMediumContrast
import com.example.financetracker.ui.theme.surfaceContainerLowestLight
import com.example.financetracker.ui.theme.surfaceContainerLowestLightHighContrast
import com.example.financetracker.ui.theme.surfaceContainerLowestLightMediumContrast
import com.example.financetracker.ui.theme.surfaceDark
import com.example.financetracker.ui.theme.surfaceDarkHighContrast
import com.example.financetracker.ui.theme.surfaceDarkMediumContrast
import com.example.financetracker.ui.theme.surfaceDimDark
import com.example.financetracker.ui.theme.surfaceDimDarkHighContrast
import com.example.financetracker.ui.theme.surfaceDimDarkMediumContrast
import com.example.financetracker.ui.theme.surfaceDimLight
import com.example.financetracker.ui.theme.surfaceDimLightHighContrast
import com.example.financetracker.ui.theme.surfaceDimLightMediumContrast
import com.example.financetracker.ui.theme.surfaceLight
import com.example.financetracker.ui.theme.surfaceLightHighContrast
import com.example.financetracker.ui.theme.surfaceLightMediumContrast
import com.example.financetracker.ui.theme.surfaceVariantDark
import com.example.financetracker.ui.theme.surfaceVariantDarkHighContrast
import com.example.financetracker.ui.theme.surfaceVariantDarkMediumContrast
import com.example.financetracker.ui.theme.surfaceVariantLight
import com.example.financetracker.ui.theme.surfaceVariantLightHighContrast
import com.example.financetracker.ui.theme.surfaceVariantLightMediumContrast
import com.example.financetracker.ui.theme.tertiaryContainerDark
import com.example.financetracker.ui.theme.tertiaryContainerDarkHighContrast
import com.example.financetracker.ui.theme.tertiaryContainerDarkMediumContrast
import com.example.financetracker.ui.theme.tertiaryContainerLight
import com.example.financetracker.ui.theme.tertiaryContainerLightHighContrast
import com.example.financetracker.ui.theme.tertiaryContainerLightMediumContrast
import com.example.financetracker.ui.theme.tertiaryDark
import com.example.financetracker.ui.theme.tertiaryDarkHighContrast
import com.example.financetracker.ui.theme.tertiaryDarkMediumContrast
import com.example.financetracker.ui.theme.tertiaryLight
import com.example.financetracker.ui.theme.tertiaryLightHighContrast
import com.example.financetracker.ui.theme.tertiaryLightMediumContrast

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

//@Immutable
//data class ColorFamily(
//    val color: Color,
//    val onColor: Color,
//    val colorContainer: Color,
//    val onColorContainer: Color
//)
//
//val unspecified_scheme = ColorFamily(
//    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
//)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      
      darkTheme -> darkScheme
      else -> lightScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content
  )
}

