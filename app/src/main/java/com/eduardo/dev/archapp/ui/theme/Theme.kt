package com.eduardo.dev.archapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Professional color palette with blues, grays and whites
private val LightColorScheme = lightColorScheme(
    primary = Blue500,
    onPrimary = Color.White,
    primaryContainer = Blue100,
    onPrimaryContainer = Blue900,
    secondary = Blue700,
    onSecondary = Color.White,
    secondaryContainer = Blue200,
    onSecondaryContainer = Blue800,
    tertiary = Gray500,
    onTertiary = Color.White,
    tertiaryContainer = Gray100,
    onTertiaryContainer = Gray900,
    background = Color.White,
    onBackground = Gray900,
    surface = Color.White,
    onSurface = Gray900,
    surfaceVariant = Gray100,
    onSurfaceVariant = Gray700,
    outline = Gray400
)

private val DarkColorScheme = darkColorScheme(
    primary = Blue300,
    onPrimary = Blue900,
    primaryContainer = Blue700,
    onPrimaryContainer = Blue100,
    secondary = Blue200,
    onSecondary = Blue800,
    secondaryContainer = Blue600,
    onSecondaryContainer = Blue100,
    tertiary = Gray300,
    onTertiary = Gray900,
    tertiaryContainer = Gray700,
    onTertiaryContainer = Gray100,
    background = Gray900,
    onBackground = Gray100,
    surface = Gray800,
    onSurface = Gray100,
    surfaceVariant = Gray800,
    onSurfaceVariant = Gray300,
    outline = Gray500
)

@Composable
fun ArchappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}