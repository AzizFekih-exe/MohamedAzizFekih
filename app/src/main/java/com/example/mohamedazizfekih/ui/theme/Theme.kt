package com.example.mohamedazizfekih.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Terracotta,
    secondary = MediterraneanBlue,
    tertiary = Olive,
    background = DarkEarth,
    surface = DarkClaySurface,
    surfaceVariant = Color(0xFF493C34),
    surfaceContainerHighest = Color(0xFF3B302A),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Sand,
    onSurface = Sand,
    onSurfaceVariant = Color(0xFFE7D3BF)
)

private val LightColorScheme = lightColorScheme(
    primary = Clay,
    secondary = MediterraneanBlue,
    tertiary = Olive,
    background = WarmIvory,
    surface = Color(0xFFFFFCF7),
    surfaceVariant = Sand,
    surfaceContainerHighest = Color(0xFFF0DDC5),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF2A211B),
    onSurface = Color(0xFF2A211B),
    onSurfaceVariant = Color(0xFF5F5045)
)

// Based on Lab 9.1 - Material Theme.
@Composable
fun MohamedAzizFekihTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    // Based on Lab 9.1 - Material Theme: choose a light or dark color scheme.
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
