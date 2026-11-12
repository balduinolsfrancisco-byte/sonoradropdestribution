package com.example.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = ElectricCyan,
    onPrimary = Color(0xFF001E28),
    primaryContainer = DeepViolet,
    onPrimaryContainer = Color.White,
    secondary = NeonPurple,
    onSecondary = Color(0xFF1E004B),
    secondaryContainer = Color(0xFF2C224C),
    onSecondaryContainer = Color.White,
    tertiary = VibrantPink,
    onTertiary = Color.White,
    background = DeepBackground,
    onBackground = TextPrimary,
    surface = SurfaceDark,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = SurfaceBorder,
    error = Color(0xFFFF5252),
    onError = Color.White
)

@Composable
fun SonoraDropTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
