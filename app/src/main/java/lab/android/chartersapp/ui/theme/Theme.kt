package com.example.compose
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
import lab.android.chartersapp.ui.theme.AppTypography
import lab.android.chartersapp.ui.theme.backgroundDark
import lab.android.chartersapp.ui.theme.backgroundDarkHighContrast
import lab.android.chartersapp.ui.theme.backgroundDarkMediumContrast
import lab.android.chartersapp.ui.theme.backgroundLight
import lab.android.chartersapp.ui.theme.backgroundLightHighContrast
import lab.android.chartersapp.ui.theme.backgroundLightMediumContrast
import lab.android.chartersapp.ui.theme.errorContainerDark
import lab.android.chartersapp.ui.theme.errorContainerDarkHighContrast
import lab.android.chartersapp.ui.theme.errorContainerDarkMediumContrast
import lab.android.chartersapp.ui.theme.errorContainerLight
import lab.android.chartersapp.ui.theme.errorContainerLightHighContrast
import lab.android.chartersapp.ui.theme.errorContainerLightMediumContrast
import lab.android.chartersapp.ui.theme.errorDark
import lab.android.chartersapp.ui.theme.errorDarkHighContrast
import lab.android.chartersapp.ui.theme.errorDarkMediumContrast
import lab.android.chartersapp.ui.theme.errorLight
import lab.android.chartersapp.ui.theme.errorLightHighContrast
import lab.android.chartersapp.ui.theme.errorLightMediumContrast
import lab.android.chartersapp.ui.theme.inverseOnSurfaceDark
import lab.android.chartersapp.ui.theme.inverseOnSurfaceDarkHighContrast
import lab.android.chartersapp.ui.theme.inverseOnSurfaceDarkMediumContrast
import lab.android.chartersapp.ui.theme.inverseOnSurfaceLight
import lab.android.chartersapp.ui.theme.inverseOnSurfaceLightHighContrast
import lab.android.chartersapp.ui.theme.inverseOnSurfaceLightMediumContrast
import lab.android.chartersapp.ui.theme.inversePrimaryDark
import lab.android.chartersapp.ui.theme.inversePrimaryDarkHighContrast
import lab.android.chartersapp.ui.theme.inversePrimaryDarkMediumContrast
import lab.android.chartersapp.ui.theme.inversePrimaryLight
import lab.android.chartersapp.ui.theme.inversePrimaryLightHighContrast
import lab.android.chartersapp.ui.theme.inversePrimaryLightMediumContrast
import lab.android.chartersapp.ui.theme.inverseSurfaceDark
import lab.android.chartersapp.ui.theme.inverseSurfaceDarkHighContrast
import lab.android.chartersapp.ui.theme.inverseSurfaceDarkMediumContrast
import lab.android.chartersapp.ui.theme.inverseSurfaceLight
import lab.android.chartersapp.ui.theme.inverseSurfaceLightHighContrast
import lab.android.chartersapp.ui.theme.inverseSurfaceLightMediumContrast
import lab.android.chartersapp.ui.theme.onBackgroundDark
import lab.android.chartersapp.ui.theme.onBackgroundDarkHighContrast
import lab.android.chartersapp.ui.theme.onBackgroundDarkMediumContrast
import lab.android.chartersapp.ui.theme.onBackgroundLight
import lab.android.chartersapp.ui.theme.onBackgroundLightHighContrast
import lab.android.chartersapp.ui.theme.onBackgroundLightMediumContrast
import lab.android.chartersapp.ui.theme.onErrorContainerDark
import lab.android.chartersapp.ui.theme.onErrorContainerDarkHighContrast
import lab.android.chartersapp.ui.theme.onErrorContainerDarkMediumContrast
import lab.android.chartersapp.ui.theme.onErrorContainerLight
import lab.android.chartersapp.ui.theme.onErrorContainerLightHighContrast
import lab.android.chartersapp.ui.theme.onErrorContainerLightMediumContrast
import lab.android.chartersapp.ui.theme.onErrorDark
import lab.android.chartersapp.ui.theme.onErrorDarkHighContrast
import lab.android.chartersapp.ui.theme.onErrorDarkMediumContrast
import lab.android.chartersapp.ui.theme.onErrorLight
import lab.android.chartersapp.ui.theme.onErrorLightHighContrast
import lab.android.chartersapp.ui.theme.onErrorLightMediumContrast
import lab.android.chartersapp.ui.theme.onPrimaryContainerDark
import lab.android.chartersapp.ui.theme.onPrimaryContainerDarkHighContrast
import lab.android.chartersapp.ui.theme.onPrimaryContainerDarkMediumContrast
import lab.android.chartersapp.ui.theme.onPrimaryContainerLight
import lab.android.chartersapp.ui.theme.onPrimaryContainerLightHighContrast
import lab.android.chartersapp.ui.theme.onPrimaryContainerLightMediumContrast
import lab.android.chartersapp.ui.theme.onPrimaryDark
import lab.android.chartersapp.ui.theme.onPrimaryDarkHighContrast
import lab.android.chartersapp.ui.theme.onPrimaryDarkMediumContrast
import lab.android.chartersapp.ui.theme.onPrimaryLight
import lab.android.chartersapp.ui.theme.onPrimaryLightHighContrast
import lab.android.chartersapp.ui.theme.onPrimaryLightMediumContrast
import lab.android.chartersapp.ui.theme.onSecondaryContainerDark
import lab.android.chartersapp.ui.theme.onSecondaryContainerDarkHighContrast
import lab.android.chartersapp.ui.theme.onSecondaryContainerDarkMediumContrast
import lab.android.chartersapp.ui.theme.onSecondaryContainerLight
import lab.android.chartersapp.ui.theme.onSecondaryContainerLightHighContrast
import lab.android.chartersapp.ui.theme.onSecondaryContainerLightMediumContrast
import lab.android.chartersapp.ui.theme.onSecondaryDark
import lab.android.chartersapp.ui.theme.onSecondaryDarkHighContrast
import lab.android.chartersapp.ui.theme.onSecondaryDarkMediumContrast
import lab.android.chartersapp.ui.theme.onSecondaryLight
import lab.android.chartersapp.ui.theme.onSecondaryLightHighContrast
import lab.android.chartersapp.ui.theme.onSecondaryLightMediumContrast
import lab.android.chartersapp.ui.theme.onSurfaceDark
import lab.android.chartersapp.ui.theme.onSurfaceDarkHighContrast
import lab.android.chartersapp.ui.theme.onSurfaceDarkMediumContrast
import lab.android.chartersapp.ui.theme.onSurfaceLight
import lab.android.chartersapp.ui.theme.onSurfaceLightHighContrast
import lab.android.chartersapp.ui.theme.onSurfaceLightMediumContrast
import lab.android.chartersapp.ui.theme.onSurfaceVariantDark
import lab.android.chartersapp.ui.theme.onSurfaceVariantDarkHighContrast
import lab.android.chartersapp.ui.theme.onSurfaceVariantDarkMediumContrast
import lab.android.chartersapp.ui.theme.onSurfaceVariantLight
import lab.android.chartersapp.ui.theme.onSurfaceVariantLightHighContrast
import lab.android.chartersapp.ui.theme.onSurfaceVariantLightMediumContrast
import lab.android.chartersapp.ui.theme.onTertiaryContainerDark
import lab.android.chartersapp.ui.theme.onTertiaryContainerDarkHighContrast
import lab.android.chartersapp.ui.theme.onTertiaryContainerDarkMediumContrast
import lab.android.chartersapp.ui.theme.onTertiaryContainerLight
import lab.android.chartersapp.ui.theme.onTertiaryContainerLightHighContrast
import lab.android.chartersapp.ui.theme.onTertiaryContainerLightMediumContrast
import lab.android.chartersapp.ui.theme.onTertiaryDark
import lab.android.chartersapp.ui.theme.onTertiaryDarkHighContrast
import lab.android.chartersapp.ui.theme.onTertiaryDarkMediumContrast
import lab.android.chartersapp.ui.theme.onTertiaryLight
import lab.android.chartersapp.ui.theme.onTertiaryLightHighContrast
import lab.android.chartersapp.ui.theme.onTertiaryLightMediumContrast
import lab.android.chartersapp.ui.theme.outlineDark
import lab.android.chartersapp.ui.theme.outlineDarkHighContrast
import lab.android.chartersapp.ui.theme.outlineDarkMediumContrast
import lab.android.chartersapp.ui.theme.outlineLight
import lab.android.chartersapp.ui.theme.outlineLightHighContrast
import lab.android.chartersapp.ui.theme.outlineLightMediumContrast
import lab.android.chartersapp.ui.theme.outlineVariantDark
import lab.android.chartersapp.ui.theme.outlineVariantDarkHighContrast
import lab.android.chartersapp.ui.theme.outlineVariantDarkMediumContrast
import lab.android.chartersapp.ui.theme.outlineVariantLight
import lab.android.chartersapp.ui.theme.outlineVariantLightHighContrast
import lab.android.chartersapp.ui.theme.outlineVariantLightMediumContrast
import lab.android.chartersapp.ui.theme.primaryContainerDark
import lab.android.chartersapp.ui.theme.primaryContainerDarkHighContrast
import lab.android.chartersapp.ui.theme.primaryContainerDarkMediumContrast
import lab.android.chartersapp.ui.theme.primaryContainerLight
import lab.android.chartersapp.ui.theme.primaryContainerLightHighContrast
import lab.android.chartersapp.ui.theme.primaryContainerLightMediumContrast
import lab.android.chartersapp.ui.theme.primaryDark
import lab.android.chartersapp.ui.theme.primaryDarkHighContrast
import lab.android.chartersapp.ui.theme.primaryDarkMediumContrast
import lab.android.chartersapp.ui.theme.primaryLight
import lab.android.chartersapp.ui.theme.primaryLightHighContrast
import lab.android.chartersapp.ui.theme.primaryLightMediumContrast
import lab.android.chartersapp.ui.theme.scrimDark
import lab.android.chartersapp.ui.theme.scrimDarkHighContrast
import lab.android.chartersapp.ui.theme.scrimDarkMediumContrast
import lab.android.chartersapp.ui.theme.scrimLight
import lab.android.chartersapp.ui.theme.scrimLightHighContrast
import lab.android.chartersapp.ui.theme.scrimLightMediumContrast
import lab.android.chartersapp.ui.theme.secondaryContainerDark
import lab.android.chartersapp.ui.theme.secondaryContainerDarkHighContrast
import lab.android.chartersapp.ui.theme.secondaryContainerDarkMediumContrast
import lab.android.chartersapp.ui.theme.secondaryContainerLight
import lab.android.chartersapp.ui.theme.secondaryContainerLightHighContrast
import lab.android.chartersapp.ui.theme.secondaryContainerLightMediumContrast
import lab.android.chartersapp.ui.theme.secondaryDark
import lab.android.chartersapp.ui.theme.secondaryDarkHighContrast
import lab.android.chartersapp.ui.theme.secondaryDarkMediumContrast
import lab.android.chartersapp.ui.theme.secondaryLight
import lab.android.chartersapp.ui.theme.secondaryLightHighContrast
import lab.android.chartersapp.ui.theme.secondaryLightMediumContrast
import lab.android.chartersapp.ui.theme.surfaceBrightDark
import lab.android.chartersapp.ui.theme.surfaceBrightDarkHighContrast
import lab.android.chartersapp.ui.theme.surfaceBrightDarkMediumContrast
import lab.android.chartersapp.ui.theme.surfaceBrightLight
import lab.android.chartersapp.ui.theme.surfaceBrightLightHighContrast
import lab.android.chartersapp.ui.theme.surfaceBrightLightMediumContrast
import lab.android.chartersapp.ui.theme.surfaceContainerDark
import lab.android.chartersapp.ui.theme.surfaceContainerDarkHighContrast
import lab.android.chartersapp.ui.theme.surfaceContainerDarkMediumContrast
import lab.android.chartersapp.ui.theme.surfaceContainerHighDark
import lab.android.chartersapp.ui.theme.surfaceContainerHighDarkHighContrast
import lab.android.chartersapp.ui.theme.surfaceContainerHighDarkMediumContrast
import lab.android.chartersapp.ui.theme.surfaceContainerHighLight
import lab.android.chartersapp.ui.theme.surfaceContainerHighLightHighContrast
import lab.android.chartersapp.ui.theme.surfaceContainerHighLightMediumContrast
import lab.android.chartersapp.ui.theme.surfaceContainerHighestDark
import lab.android.chartersapp.ui.theme.surfaceContainerHighestDarkHighContrast
import lab.android.chartersapp.ui.theme.surfaceContainerHighestDarkMediumContrast
import lab.android.chartersapp.ui.theme.surfaceContainerHighestLight
import lab.android.chartersapp.ui.theme.surfaceContainerHighestLightHighContrast
import lab.android.chartersapp.ui.theme.surfaceContainerHighestLightMediumContrast
import lab.android.chartersapp.ui.theme.surfaceContainerLight
import lab.android.chartersapp.ui.theme.surfaceContainerLightHighContrast
import lab.android.chartersapp.ui.theme.surfaceContainerLightMediumContrast
import lab.android.chartersapp.ui.theme.surfaceContainerLowDark
import lab.android.chartersapp.ui.theme.surfaceContainerLowDarkHighContrast
import lab.android.chartersapp.ui.theme.surfaceContainerLowDarkMediumContrast
import lab.android.chartersapp.ui.theme.surfaceContainerLowLight
import lab.android.chartersapp.ui.theme.surfaceContainerLowLightHighContrast
import lab.android.chartersapp.ui.theme.surfaceContainerLowLightMediumContrast
import lab.android.chartersapp.ui.theme.surfaceContainerLowestDark
import lab.android.chartersapp.ui.theme.surfaceContainerLowestDarkHighContrast
import lab.android.chartersapp.ui.theme.surfaceContainerLowestDarkMediumContrast
import lab.android.chartersapp.ui.theme.surfaceContainerLowestLight
import lab.android.chartersapp.ui.theme.surfaceContainerLowestLightHighContrast
import lab.android.chartersapp.ui.theme.surfaceContainerLowestLightMediumContrast
import lab.android.chartersapp.ui.theme.surfaceDark
import lab.android.chartersapp.ui.theme.surfaceDarkHighContrast
import lab.android.chartersapp.ui.theme.surfaceDarkMediumContrast
import lab.android.chartersapp.ui.theme.surfaceDimDark
import lab.android.chartersapp.ui.theme.surfaceDimDarkHighContrast
import lab.android.chartersapp.ui.theme.surfaceDimDarkMediumContrast
import lab.android.chartersapp.ui.theme.surfaceDimLight
import lab.android.chartersapp.ui.theme.surfaceDimLightHighContrast
import lab.android.chartersapp.ui.theme.surfaceDimLightMediumContrast
import lab.android.chartersapp.ui.theme.surfaceLight
import lab.android.chartersapp.ui.theme.surfaceLightHighContrast
import lab.android.chartersapp.ui.theme.surfaceLightMediumContrast
import lab.android.chartersapp.ui.theme.surfaceVariantDark
import lab.android.chartersapp.ui.theme.surfaceVariantDarkHighContrast
import lab.android.chartersapp.ui.theme.surfaceVariantDarkMediumContrast
import lab.android.chartersapp.ui.theme.surfaceVariantLight
import lab.android.chartersapp.ui.theme.surfaceVariantLightHighContrast
import lab.android.chartersapp.ui.theme.surfaceVariantLightMediumContrast
import lab.android.chartersapp.ui.theme.tertiaryContainerDark
import lab.android.chartersapp.ui.theme.tertiaryContainerDarkHighContrast
import lab.android.chartersapp.ui.theme.tertiaryContainerDarkMediumContrast
import lab.android.chartersapp.ui.theme.tertiaryContainerLight
import lab.android.chartersapp.ui.theme.tertiaryContainerLightHighContrast
import lab.android.chartersapp.ui.theme.tertiaryContainerLightMediumContrast
import lab.android.chartersapp.ui.theme.tertiaryDark
import lab.android.chartersapp.ui.theme.tertiaryDarkHighContrast
import lab.android.chartersapp.ui.theme.tertiaryDarkMediumContrast
import lab.android.chartersapp.ui.theme.tertiaryLight
import lab.android.chartersapp.ui.theme.tertiaryLightHighContrast
import lab.android.chartersapp.ui.theme.tertiaryLightMediumContrast

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

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor -> {
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

